package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element representation of the Operator type
 */
public class ElementOperator extends Element {

    /**
     * private String containing the symobol type
     */
    private final String symbol;

    /**
     * Default constructor
     *
     * @param symbol type of symbol
     */
    public ElementOperator(String symbol) {
        if (symbol == null)
            throw new IllegalArgumentException("Symbol argument can't be null reference");
        this.symbol = symbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String asText() {
        return symbol;
    }
}
