package hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators;


/**
 * Interface for implementing the strategy programming paradigm on the comparison elements in the
 * ConditionalExpressin elements
 */
public interface IComparisonOperator {

    /**
     * Arguments in methods are mutually compared to a implemented criteria. True if the criteria is met,
     * false otherwise
     *
     * @param value1 literal element string
     * @param value2 string in database to be compared with
     */
    public boolean satisfied(String value1, String value2);
}
