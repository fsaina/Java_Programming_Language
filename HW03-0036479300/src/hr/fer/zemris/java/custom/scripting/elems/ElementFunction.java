package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representation of the function logical object
 */
public class ElementFunction extends Element {

    /**
     * private String containing the name of the element
     */
    private final String name;

    /**
     * Default constructor
     *
     * @param name non-null String reference
     */
    public ElementFunction(String name) {
        if (name == null)
            throw new IllegalArgumentException("Name agrument cannot be null value");
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String asText() {
        return name;
    }
}
