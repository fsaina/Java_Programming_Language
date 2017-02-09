package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Tree writer class uses the Visitor programming paradigm to visit all the parsed nodes for a smart script
 * text
 */
public class TreeWriter {

    /**
     * Entry point of the application
     *
     * @param args command line arguments - one required, the file path to a smart script
     */
    public static void main(String[] args) {

        String docBody = null;
        SmartScriptParser parser = null;

        if (args.length != 1) {
            System.err.println("One input argument expeced - a name of the file containing the smart script");
            System.exit(-1);
        }

        try {
            docBody = new String(
                    Files.readAllBytes(Paths.get(args[0])),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            System.err.println("The filepath your entered cannot be resolved to a file");
            System.exit(-1);
        }

        try {

            parser = new SmartScriptParser(docBody);

        } catch (SmartScriptParserException e) {

            System.out.println("Unable to parse document!");
            System.exit(-1);

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);

        }

        DocumentNode document = parser.getDocumentNode();

        WriterVisitor visitor = new WriterVisitor();
        document.accept(visitor);
        System.out.println(visitor.returnGeneratedString());

        System.out.println("\n\n This was the original: \n");
        System.out.println(docBody);
    }

    /**
     * Class WriterVision implements the INodeVisitor interface used for visiting all the nodes with the
     * class
     */
    private static class WriterVisitor implements INodeVisitor {

        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public void visitTextNode(TextNode node) {
            stringBuilder.append((node.getText() + " "));
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {

            stringBuilder.append("{$");
            stringBuilder.append("FOR ");

            stringBuilder.append(node.getVariable().asText() + " ");
            stringBuilder.append(node.getStartExpression().asText() + " ");
            stringBuilder.append(node.getEndExpression().asText() + " ");
            stringBuilder.append(node.getStepExpression().asText() + " ");

            stringBuilder.append("$}\n");

            propagateToNextLevel(node);

            stringBuilder.append("{$END$}");

        }

        @Override
        public void visitEchoNode(EchoNode node) {
            stringBuilder.append("{$= ");

            for (Element elem : ((EchoNode) node).getElements()) {
                stringBuilder.append(elem.asText() + " ");
            }

            stringBuilder.append("$}");
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

        /**
         * Method returns the generated string
         *
         * @return generated string from the string builder
         */
        public String returnGeneratedString() {
            return stringBuilder.toString();
        }
    }

}


