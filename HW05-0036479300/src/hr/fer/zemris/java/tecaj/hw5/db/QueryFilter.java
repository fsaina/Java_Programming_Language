package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.ComparisonOperators.*;
import hr.fer.zemris.java.tecaj.hw5.db.FieldValueGetters.FirstNameFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.FieldValueGetters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.FieldValueGetters.JmbagFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.FieldValueGetters.LastNameFieldValueGetter;

import java.util.ArrayList;
import java.util.List;

/**
 * QueryFilter object implements string parsing for the user given string
 */
public class QueryFilter implements IFilter {

    /**
     * Private store of the used conditonExpression extracted from the string provided
     */
    private List<ConditionalExpression> conditionalExpressions;

    /**
     * Default QueryFilter constructor class
     *
     * @param query String representing the whold line user inputted
     */
    public QueryFilter(String query) {
        if (query == null)
            throw new IllegalArgumentException("String provided in the constructor is null");

        this.conditionalExpressions = new ArrayList<>();

        query = query.replaceAll("\\s+", " ").replaceAll("(?i) and ", " and ").trim();

        String[] conditionalExpressionsString = query.split("and");

        for (String conExp : conditionalExpressionsString) {

            String[] stringSplit = conExp.trim().split("\"");

            if (stringSplit.length > 2 || stringSplit.length < 1)
                throw new QueryFormatException("Illegal positioning of \" in the query");

            String literal = stringSplit[stringSplit.length - 1]; //the last element

            String operator = determineOperator(stringSplit[0]);
            String operation = stringSplit[0].replaceAll(operator, "").trim();

            IFieldValueGetter fieldName = determineFieldValue(operator);
            IComparisonOperator comparisonOperator = determineComparisonOperator(operation);

            conditionalExpressions.add(new ConditionalExpression(fieldName, literal, comparisonOperator));

        }
    }

    /*
     * Private method used for determining the Operator type
     */
    private String determineOperator(String conExp) {

        char charArray[] = conExp.trim().toCharArray();
        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < charArray.length; i++) {

            if (Character.isWhitespace(charArray[i]))
                break;

            if (Character.isAlphabetic(charArray[i]))
                sb.append(charArray[i]);

        }

        return sb.toString();
    }

    /*
     * Private method used for determining the compariosn operator from a given string
     */
    private IComparisonOperator determineComparisonOperator(String conExp) {

        switch (conExp) {

            case ">": {
                return new GreaterComparisonOperator();
            }
            case "<": {
                return new LessComparsionOperator();
            }
            case ">=": {
                return new GreaterOrEqualComparisonOperator();
            }
            case "<=": {
                return new LesserOrEqualComparisonOperator();
            }
            case "=": {
                return new EqualsComparisonOperator();
            }
            case "!=": {
                return new NotEqualComparisonOperator();
            }
            case "LIKE": {
                return new LikeComparisonOperator();
            }

        }

        throw new QueryFormatException("Operator not supported! Operator used: " + conExp);
    }

    /*
     * Private method used for determining the field value element in the string
     */
    private IFieldValueGetter determineFieldValue(String next) {

        if (next.equals("lastName")) {
            return new LastNameFieldValueGetter();
        } else if (next.equals("firstName")) {
            return new FirstNameFieldValueGetter();
        } else if (next.equals("jmbag")) {
            return new JmbagFieldValueGetter();
        } else {
            throw new QueryFormatException("The format of the " +
                    "database file has a illegal entry: " + next);
        }

    }

    /**
     * Method part of the IFilter strategy implementation. Tests all objects previously parsed for a certain
     * criteria
     *
     * @param record StudentRecord to be tested
     * @return true every element meets the satisfied criteria
     */
    @Override
    public boolean accepts(StudentRecord record) {

        //test for every element if they oblige all of the rules(keywoard is and)
        for (ConditionalExpression expression : conditionalExpressions) {
            boolean recordSatisfies = expression.getComparisonOperator().satisfied(
                    expression.getFieldValueGetter().get(record),  // returns lastName from given record
                    expression.getLiteral()
            );

            if (!recordSatisfies)
                return false;
        }

        return true;

    }
}
