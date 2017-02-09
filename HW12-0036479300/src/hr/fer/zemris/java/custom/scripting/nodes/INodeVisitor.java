package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface used as a contract for the visitor of the smart script
 */
public interface INodeVisitor {
    /**
     * Method visits the text node
     * @param node Text node given
     */
    public void visitTextNode(TextNode node);

    /**
     * Method visits the for loop node
     * @param node ForLoop node given
     */
    public void visitForLoopNode(ForLoopNode node);

    /**
     * Method visits the echo node
     * @param node echo node given
     */
    public void visitEchoNode(EchoNode node);

    /**
     * Method visits the document node
     * @param node document node given
     */
    public void visitDocumentNode(DocumentNode node);
}