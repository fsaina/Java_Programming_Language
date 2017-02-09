package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.FieldValueGetters.IFieldValueGetter;

/**
 * Class representation of a single valid element in the user inputter query.
 */
public class ConditionalExpression {

    /**
     * Field used in the expression
     */
    private IFieldValueGetter fieldValueGetter;

    /**
     * Literal used in the expression
     */
    private String literal;

    /**
     * Comparison object used in the expression
     */
    private IComparisonOperator comparisonOperator;

    /**
     * ComparionsExpression default constructor
     *
     * @param fieldValueGetter   field used in the expression
     * @param literal            literal used in the expression
     * @param comparisonOperator comparison object used in the expression
     */
    public ConditionalExpression(IFieldValueGetter fieldValueGetter, String literal, IComparisonOperator comparisonOperator) {

        testForNullValue(fieldValueGetter);
        testForNullValue(literal);
        testForNullValue(comparisonOperator);

        this.fieldValueGetter = fieldValueGetter;
        this.literal = literal;
        this.comparisonOperator = comparisonOperator;

    }

    /*
     * Private method for testing the arguments provided in the constructor
     */
    private void testForNullValue(Object object) {
        if (object == null)
            throw new IllegalArgumentException("Argument provided in the conditional expression was null");
    }

    /**
     * Getter for the field value object
     *
     * @return field value object
     */
    public IFieldValueGetter getFieldValueGetter() {
        return fieldValueGetter;
    }

    /**
     * Getter for the literal value object
     *
     * @return String representing the literal
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Getter fot the comparison object
     *
     * @return comparison object
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }
}
