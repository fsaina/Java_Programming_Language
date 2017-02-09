package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representation of the Integer value
 */
public class ElementConstantInteger extends Element {

    /**
     * private variable containing the value of the number
     */
    private final int value;

    /**
     * Default constructor
     *
     * @param value number to be stored
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String asText() {
        return new Integer(value).toString();
    }
}
