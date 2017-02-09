package hr.fer.zemris.java.hw3;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * SmartScriptTester class implementation to be used as a testing ground for the Parser, Lexer and all
 * necessary objects required for them to work
 */
public class SmartScriptTester {


    /**
     * Entry point of the applicaiton
     *
     * @param argv single line referring the path to the input txt. An sample doc was provided in the
     *             'examples' folder, inside the root of this project
     */
    public static void main(String argv[]) {

        String docBody = "";
        SmartScriptParser parser = null;

        if (argv.length != 1)
            throw new IllegalArgumentException("Illegal number of argument passed to the application");

        try {
            docBody = new String(
                    Files.readAllBytes(Paths.get(argv[0])),
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
        String originalDocumentBody = createOriginalDocumentBody(document);

        System.out.println(originalDocumentBody); // should write something like original
        // content of docBody

        System.out.println("\nOriginal input:\n" + docBody);
    }

    /**
     * Static class used for printing out the elements of a documentNode. PLEASE NOTE, this method is
     * implemented for the testing purposes of the provided specifications and examples of the third java
     * assignment.Therefore printing, for instance, a 3 level tree doc structure fill not give appropriete
     * output.
     *
     * @param documentNode root node of the parsed document
     * @return String output of the parsed document
     */
    static String createOriginalDocumentBody(DocumentNode documentNode) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < documentNode.numberOfChildren(); i++) {
            Node node = documentNode.getChild(i);


            if (node instanceof TextNode)
                stringBuilder.append(((TextNode) node).getText() + " ");


            else if (node instanceof ForLoopNode) {

                ForLoopNode nodeReference = (ForLoopNode) node;
                stringBuilder.append("{$");
                stringBuilder.append("FOR ");

                stringBuilder.append(nodeReference.getVariable().asText() + " ");
                stringBuilder.append(nodeReference.getStartExpression().asText() + " ");
                stringBuilder.append(nodeReference.getEndExpression().asText() + " ");
                stringBuilder.append(nodeReference.getStepExpression().asText() + " ");

                stringBuilder.append("$}");

                for (int j = 0; j < (node).numberOfChildren(); j++) {
                    Node nodeSub = (node).getChild(j);
                    if (nodeSub instanceof TextNode)
                        stringBuilder.append(((TextNode) nodeSub).getText());
                    else if (nodeSub instanceof EchoNode) {

                        stringBuilder.append("{$= ");


                        for (Element elem : ((EchoNode) nodeSub).getElements()) {
                            stringBuilder.append(elem.asText() + " ");
                        }

                        stringBuilder.append("$}");
                    }

                }

                stringBuilder.append("{$END$}");
            }
        }

        return stringBuilder.toString();
    }
}
