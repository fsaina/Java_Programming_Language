package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class representation of a node containing text
 */
public class TextNode extends Node {

    /**
     * Variable containing the text
     */
    private final String text;

    /**
     *Default constructor
     * @param text text to be stored
     */
    public TextNode(String text){
        if(text == null)
            throw new IllegalArgumentException("Text argument should not be null");
        this.text = text;
    }

    /**
     * Getter method for text element inside
     * @return String containing the text
     */
    public String getText(){
        return text;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitTextNode(this);
    }
}
