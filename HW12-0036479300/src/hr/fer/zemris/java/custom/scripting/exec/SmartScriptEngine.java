package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

/**
 * Smart script engine parser implementation class
 *
 * @author Filip Saina
 */
public class SmartScriptEngine {

    /**
     * Reference to the document node
     */
    private DocumentNode documentNode;

    /**
     * Reference to the request context
     */
    private RequestContext requestContext;

    /**
     * Reference to the multistack object used for data manipulation
     */
    private ObjectMultistack multistack = new ObjectMultistack();

    /**
     * Reference to the visitor imlementation
     */
    private INodeVisitor visitor = new NodeExecutor();

    /**
     * Default calss constructor
     *
     * @param documentNode   reference to the document node
     * @param requestContext reference to the request context
     */
    public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        if (documentNode == null)
            throw new IllegalArgumentException("Document node argument cannot be null");

        if (requestContext == null)
            throw new IllegalArgumentException("Request context argument cannot be null");

        this.documentNode = documentNode;
        this.requestContext = requestContext;
    }

    /**
     * Method executes the preloaded script
     */
    public void execute() {
        documentNode.accept(visitor);
    }

    /**
     * Implementation of the Visitor object used for visition smart script logical component nodes within the
     * file
     */
    private class NodeExecutor implements INodeVisitor {

        @Override
        public void visitTextNode(TextNode node) {
            try {
                requestContext.write(node.getText());
            } catch (IOException e) {
                System.err.println("Error visiting text node element");
                System.exit(-1);
            }
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {

            String variableName = node.getVariable().asText();

            Element startExpression = node.getStartExpression();
            Element endExpression = node.getEndExpression();
            Element stepExpression = node.getStepExpression();

            ValueWrapper currentValue;

            multistack.push(variableName, new ValueWrapper(startExpression.asText()));

            while ((currentValue = multistack.peek(variableName)).numCompare(endExpression.asText()) <= 0) {

                //execute every child
                propagateToNextLevel(node);

                //increment and push to stack
                currentValue.increment(stepExpression.asText());
                multistack.push(variableName, currentValue);
            }

            multistack.pop(variableName);
        }

        @Override
        public void visitEchoNode(EchoNode node) {

            Stack<ValueWrapper> tmpStack = new Stack<>();
            Stack<ValueWrapper> reversedTmpStack = new Stack<>();


            for (Element token : node.getElements()) {

                if (token instanceof ElementString ||
                        token instanceof ElementConstantInteger ||
                        token instanceof ElementConstantDouble) {

                    tmpStack.push(new ValueWrapper(token.asText()));
                } else if (token instanceof ElementVariable) {
                    ValueWrapper value = multistack.peek(((ElementVariable) token).asText());
                    tmpStack.push(new ValueWrapper(value.getValue()));
                } else if (token instanceof ElementOperator) {

                    ValueWrapper op1 = tmpStack.pop();
                    ValueWrapper op2 = tmpStack.pop();

                    switch (token.asText()) {

                        case "+": {
                            op1.increment(op2.getValue());
                            break;
                        }
                        case "-": {
                            op1.decrement(op2.getValue());
                            break;
                        }
                        case "*": {
                            op1.multiply(op2.getValue());
                            break;
                        }
                        case "/": {
                            op1.divide(op2.getValue());
                            break;
                        }
                    }

                    tmpStack.push(op1);
                } else if (token instanceof ElementFunction) {


                    switch (token.asText()) {

                        case "sin": {
                            ValueWrapper X = tmpStack.pop();
                            double x = (double) Double.valueOf(X.getValue().toString());
                            tmpStack.push(new ValueWrapper(Math.sin(Math.toRadians(x))));
                            break;
                        }

                        case "paramGet": {
                            double defValue = (double) Double.valueOf(tmpStack.pop().getValue().toString());
                            ValueWrapper name = tmpStack.pop();

                            String value = requestContext.getParameter(name.getValue().toString());
                            if (value != null) {
                                tmpStack.push(new ValueWrapper(value));
                            } else {
                                tmpStack.push(new ValueWrapper(defValue));
                            }
                            break;
                        }

                        case "decfmt": {

                            String format = (String) tmpStack.pop().getValue();
                            Object number = tmpStack.pop().getValue();

                            DecimalFormat fmt = new DecimalFormat(format);
                            tmpStack.push(new ValueWrapper(fmt.format(number)));
                            break;
                        }

                        case "dup": {

                            ValueWrapper object = tmpStack.pop();
                            tmpStack.push(object);
                            tmpStack.push(object);
                            break;
                        }

                        case "swap": {

                            ValueWrapper a = tmpStack.pop();
                            ValueWrapper b = tmpStack.pop();

                            tmpStack.push(a);
                            tmpStack.push(b);
                            break;
                        }

                        case "setMimeType": {
                            String x = (String) tmpStack.pop().getValue();
                            requestContext.setMimeType(x);
                            break;
                        }

                        case "pparamGet": {

                            double defValue = (double) Double.valueOf(tmpStack.pop().getValue().toString());
                            ValueWrapper name = tmpStack.pop();

                            String value = requestContext.getPersistentParameter(name.getValue().toString());
                            if (value != null) {
                                tmpStack.push(new ValueWrapper(value));
                            } else {
                                tmpStack.push(new ValueWrapper(defValue));
                            }
                            break;
                        }

                        case "pparamSet": {

                            String name = tmpStack.pop().getValue().toString();
                            String defValue = tmpStack.pop().getValue().toString();

                            requestContext.setPersistentParameter(name, defValue);
                            break;
                        }

                        case "pparamDel": {

                            String name = (String) tmpStack.pop().getValue();

                            requestContext.removePersistentParameter(name);
                            break;
                        }

                        case "tparamGet": {

                            double defValue = (double) Double.valueOf(tmpStack.pop().getValue().toString());
                            ValueWrapper name = tmpStack.pop();

                            String value = requestContext.getTemporaryParameter(name.getValue().toString());
                            if (value != null) {
                                tmpStack.push(new ValueWrapper(value));
                            } else {
                                tmpStack.push(new ValueWrapper(defValue));
                            }
                            break;

                        }

                        case "tparamSet": {
                            String name = tmpStack.pop().getValue().toString();
                            String defValue = tmpStack.pop().getValue().toString();

                            requestContext.setTemporaryParameter(name, defValue);
                            break;
                        }

                        case "tparamDel": {

                            String name = tmpStack.pop().getValue().toString();

                            requestContext.removeTemporaryParameter(name);
                            break;
                        }

                    }

                }
            }

            while (!tmpStack.empty()) {
                reversedTmpStack.push(tmpStack.pop());
            }

            while (!reversedTmpStack.empty()) {
                try {
                    requestContext.write(reversedTmpStack.pop().getValue().toString());
                } catch (IOException e) {
                    System.err.println("Error writing to the reverse stack");
                    System.exit(-1);
                }
            }
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            propagateToNextLevel(node);
        }

        /**
         * Method used for propagating the content to the next level of every child and visit that node
         *
         * @param node Node element to propagate
         */
        private void propagateToNextLevel(Node node) {
            for (int j = 0; j < (node).numberOfChildren(); j++) {

                Node node1 = node.getChild(j);

                if (node1 instanceof TextNode) {
                    visitTextNode((TextNode) node1);
                } else if (node1 instanceof ForLoopNode) {
                    visitForLoopNode((ForLoopNode) node1);
                } else if (node1 instanceof EchoNode) {
                    visitEchoNode((EchoNode) node1);
                } else {
                    visitDocumentNode((DocumentNode) node1);
                }

            }
        }
    }
}