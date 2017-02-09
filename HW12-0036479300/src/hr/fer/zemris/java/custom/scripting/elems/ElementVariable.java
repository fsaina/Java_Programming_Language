package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representation of the variable type
 */
public class ElementVariable extends Element {

    /**
     * private variable containing the name String
     */
    private final String name;

    /**
     * Default constructor
     *
     * @param name String containing the name of the element
     */
    public ElementVariable(String name) {
        if (name == null)
            throw new IllegalArgumentException("Name argument can't be a null reference");
        this.name = name;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String asText() {
        return name;
    }
}
