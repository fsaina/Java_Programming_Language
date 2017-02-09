package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representation of the String element
 */
public class ElementString extends Element {

    /**
     * private variable containing the value of the element
     */
    private final String value;

    /**
     * Default constructor
     *
     * @param text non-null String reference
     */
    public ElementString(String text) {
        if (text == null)
            throw new IllegalArgumentException("Text agrument cannot be null value");
        this.value = text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String asText() {
        return value;
    }
}
