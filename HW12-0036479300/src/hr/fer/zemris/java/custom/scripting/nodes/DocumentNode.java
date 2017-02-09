package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Document node representation for the tree-like value extraction.
 * Root element of the structure.
 */
public class DocumentNode extends Node {
    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitDocumentNode(this);
    }
}
