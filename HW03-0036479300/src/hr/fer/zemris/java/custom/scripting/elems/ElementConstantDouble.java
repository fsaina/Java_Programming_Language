package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representation for the Double type element
 */
public class ElementConstantDouble extends Element {

    /**
     * private variable containing the double
     */
    private final double value;

    /**
     * Default constructor
     *
     * @param value doubel value to be passed
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String asText() {
        return new Double(value).toString();
    }
}
