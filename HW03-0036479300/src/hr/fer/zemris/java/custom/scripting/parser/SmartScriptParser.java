package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

import java.util.Arrays;

import static hr.fer.zemris.java.custom.scripting.lexer.TokenType.*;

/**
 * SmartScriptParser class implementation that uses Lexers provided by the Lexer class. Parser specification
 * are described in the third java homework.
 */
public class SmartScriptParser {

    /**
     * private lexer class to process documents
     */
    private Lexer lexer;

    /**
     * Reference to the root node of the processed elements
     */
    private DocumentNode documentNode;


    /**
     * SmartScriptParser constructor class
     *
     * @param documentBody non null String reference to a strin to be processed
     */
    public SmartScriptParser(String documentBody) {
        if (documentBody == null)
            throw new IllegalArgumentException("Null reference instead of string is not a valid argument");

        this.lexer = new Lexer(documentBody);
        this.documentNode = new DocumentNode();

        //call the parsing method on initialization
        parse();
    }

    /**
     * Method for parsing the provided document in the constructor
     */
    private void parse() {
        Token currentToken;
        Node currentNode = documentNode;

        ObjectStack objectStack = new ObjectStack();
        objectStack.push(documentNode);

        do {
            currentToken = lexer.nextToken();

            if (currentToken.getValue() == LexerSpecialSymbol.OPEN_SYMBOL)
                lexer.setState(LexerState.TAG);
            else if (currentToken.getValue() == LexerSpecialSymbol.CLOSE_SYMBOL)
                lexer.setState(LexerState.TEXT);


            switch (currentToken.getType()) {
                case TEXT:
                    //create an representation and add as a child to the currentNode

                    TextNode textNode = new TextNode((String) currentToken.getValue());
                    currentNode.addChildNode(textNode);

                    break;
                case TAG:
                    //Determine type of TAG element

                    if (currentToken.getValue() == LexerTagType.FOR) {
                        ForLoopNode forLoopNode;
                        ArrayIndexedCollection elements = new ArrayIndexedCollection();

                        //get the first element value
                        Object variable = (lexer.nextToken()).getValue();
                        if (!(variable instanceof ElementVariable))
                            throw new SmartScriptParserException("For loop format is not formatted correctly");

                        //get the next two element(they MUST occur)
                        for (int i = 0; i < 2; i++) {
                            Object element = (lexer.nextToken()).getValue();

                            if (!(element instanceof Element))
                                throw new SmartScriptParserException("For loop format is not formatted correctly");

                            Element element1 = (Element) element;
                            elements.add(element1);
                        }

                        //third element is optional
                        Object element = (currentToken = lexer.nextToken()).getValue();
                        if (element == LexerSpecialSymbol.CLOSE_SYMBOL) {
                            //ForLoopNode with 3 elements

                            try {
                                forLoopNode = new ForLoopNode((ElementVariable) variable, (Element) elements.get(0), (Element) elements.get(1));
                            }catch (IllegalArgumentException e){
                                throw new SmartScriptParserException("Illegal format of the input document");
                            }

                        } else if (element instanceof Element) {
                            //ForLoopNode with 4 elements

                            try {
                                forLoopNode = new ForLoopNode((ElementVariable) variable, (Element) elements.get(0), (Element) elements.get(1), (Element) element);
                            }catch (IllegalArgumentException e){
                                throw new SmartScriptParserException("Illegal format of the input document");
                            }

                        } else {

                            throw new SmartScriptParserException("For loop format is not formatted correctly");
                        }

                        currentNode.addChildNode(forLoopNode);
                        objectStack.push(forLoopNode);
                        currentNode = forLoopNode;

                    } else if (currentToken.getValue() == LexerTagType.ECHO) {
                        //Resolve a ECHO node

                        ArrayIndexedCollection elements = new ArrayIndexedCollection();
                        Element[] elementsArray;

                        while ((currentToken = lexer.nextToken()).getValue() != LexerSpecialSymbol.CLOSE_SYMBOL) {

                            elements.add(currentToken.getValue());
                        }

                        //copy the elements
                        elementsArray = Arrays.copyOf(elements.toArray(), elements.size(), Element[].class);

                        EchoNode echoNode = new EchoNode(elementsArray);
                        currentNode.addChildNode(echoNode);

                        //close_symbol was reached so its needed to set the state of the lexer
                        lexer.setState(LexerState.TEXT);

                    } else if (currentToken.getValue() == LexerTagType.END) {

                        objectStack.pop();
                        currentNode = (Node) objectStack.peek();

                    }
            }
        } while (currentToken.getType() != EOF);
    }

    /**
     * Getter method for the documentNode
     * @return DocumentNode reference
     */

    public DocumentNode getDocumentNode() {
        return documentNode;
    }
}
