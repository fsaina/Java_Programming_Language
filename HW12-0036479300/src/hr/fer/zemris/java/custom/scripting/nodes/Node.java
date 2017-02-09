package hr.fer.zemris.java.custom.scripting.nodes;


import java.util.ArrayList;
import java.util.List;

/**
 * General node class for the tree-like parser structure
 */
public abstract class Node {

    /**
     * Inner data storage implementation
     */
    private List<Node> arrayIndexedCollection = null;

    /**
     * Adds a new child to the private collection class
     *
     * @param child Node to be added
     * @throws IllegalArgumentException argument is of type null
     */
    public void addChildNode(Node child) {
        if (child == null)
            throw new IllegalArgumentException("Child argument cannot be a null reference");
        if (arrayIndexedCollection == null)
            arrayIndexedCollection = new ArrayList<Node>();
        arrayIndexedCollection.add(child);
    }

    /**
     * Returns the current number of children in the private collection class
     *
     * @return number of elements
     */
    public int numberOfChildren() {
        if (arrayIndexedCollection == null)
            throw new IllegalArgumentException("ArrayIndexedCollection is not yet initialized");
        return arrayIndexedCollection.size();
    }

    /**
     * Get a Node at a specific index within the collection class
     *
     * @param index i-th number of the element
     * @return Node that was returned
     */
    public Node getChild(int index) {
        return (Node) arrayIndexedCollection.get(index);
    }

    public abstract void accept(INodeVisitor visitor);
}

