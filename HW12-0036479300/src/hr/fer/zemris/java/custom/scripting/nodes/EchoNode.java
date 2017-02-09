package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically
 */
public class EchoNode extends Node {

    /**
     * elements array of which it consists
     */
    private final Element[] elements;

    /**
     * Constructor for the class EchoNode
     * @param elements array of Element objects to be added to the structure
     */
    public EchoNode(Element[] elements) {
        if (elements == null)
            throw new IllegalArgumentException("Null argument is not allowed");
        this.elements = elements;
    }

    /**
     * Getter for the elements of the EchoNode
     *
     * @return Elements array with all of its elements
     */
    public Element[] getElements() {
        return elements;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitEchoNode(this);
    }
}
