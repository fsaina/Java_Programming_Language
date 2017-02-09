package hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators;

/**
 * Not equals object representation implementation
 */
public class NotEqualComparisonOperator implements IComparisonOperator {

    /**
     * Lexical comparison of given strings to determine if not equal is true.
     *
     * @param value1 literal element string
     * @param value2 string in database to be compared with
     * @return true if value1 is lexically not equal than value2
     */
    @Override
    public boolean satisfied(String value1, String value2) {

        if (value1.compareTo(value2) == 0)
            return false;

        return true;

    }
}
