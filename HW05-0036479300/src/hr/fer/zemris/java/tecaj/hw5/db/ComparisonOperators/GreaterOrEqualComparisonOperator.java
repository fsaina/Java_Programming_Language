package hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators;

/**
 * Greater or equals Object representation class
 */
public class GreaterOrEqualComparisonOperator implements IComparisonOperator {

    /**
     * Lexical comparison of given strings to determine if greater or equal is true.
     *
     * @param value1 literal element string
     * @param value2 string in database to be compared with
     * @return true if value1 is lexically greater or equal than value2
     */
    @Override
    public boolean satisfied(String value1, String value2) {

        if(value1.compareTo(value2) > 0 || value1.compareTo(value2) == 0)
            return true;

        return false;
    }
}
