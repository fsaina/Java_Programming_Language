package hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators;

/**
 * Like operation implementation class
 */
public class LikeComparisonOperator implements IComparisonOperator {

    /**
     * Lexical comparison of given strings to determine if the value1 string
     * it like the value2 string. Value1 string can contain literal expression
     * like '*' for replacing parts of strings
     *
     * @param value1 literal element string
     * @param value2 string in database to be compared with
     * @return true if value1 is lexically like the value2
     */
    @Override
    public boolean satisfied(String value2, String value1) {

        if (value1.startsWith("*")) {
            String pureFilter = value1.replace("*", "");
            if (value2.endsWith(pureFilter))
                return true;

        } else if (value1.endsWith("*")) {
            String pureFilter = value1.replace("*", "");
            if (value2.startsWith(pureFilter))
                return true;

        } else if (value1.contains("*")) {
            //somewhere in between
            String[] values = value1.split("\\*");
            if (value2.startsWith(values[0]) && value2.endsWith(values[1]))
                return true;
        }

        return false;
    }
}
