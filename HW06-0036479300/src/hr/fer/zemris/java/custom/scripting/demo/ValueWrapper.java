package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.demo.ArithmeticOperation;;

/**
 * ValueWrapper object for representing a value with additional support for operation execution.
 */
public class ValueWrapper {

    /**
     * Current value stored
     */
    private Object value;

    /**
     * Default constructor for the ValueWrapper object
     *
     * @param o object to be stored, must be a Integer, Double, String
     */
    public ValueWrapper(Object o) {
        if (o == null)
            this.value = new Integer(0);

        if (!(isValidClassNotNullClass(o)))
            throw new RuntimeException("Constructor parameter is not " +
                    "valid class type: Integer, Double, String");

        this.value = o;
    }

    /**
     * Increments the current object value with the given one
     *
     * @param incValue given value to increment
     */
    public void increment(Object incValue) {
        arithmeticOperation(incValue, ((argument1, argument2) -> argument1 + argument2));
    }

    /**
     * Decrements the current object value with the given one
     *
     * @param decValue given value to decrement
     */
    public void decrement(Object decValue) {
        arithmeticOperation(decValue, ((argument1, argument2) -> argument1 - argument2));
    }

    /**
     * Multiplies the current object value with the given one
     *
     * @param mulValue given value to muliply
     */
    public void multiply(Object mulValue) {
        arithmeticOperation(mulValue, (argument1, argument2) -> argument1 * argument2);
    }

    /**
     * Divides the current object value with the given one
     *
     * @param divValue given value to divide
     */
    public void divide(Object divValue) {
        arithmeticOperation(divValue, ((argument1, argument2) -> {
            if (argument2 == 0)
                throw new ArithmeticException("Illegal division by zero");

            return argument1 / argument2;
        }));
    }

    /**
     * Method compares two given value if they are Integer, Double or String representations
     *
     * @param withValue object value to be compared
     * @return negative value is currently stored value is smaller that given, zero if equal, positive value
     * otherwise
     */
    public int numCompare(Object withValue) {
        Object tmpVal = value;
        if (tmpVal == null && withValue == null)
            return 0;

        if (tmpVal == null)
            tmpVal = new Integer(0);

        if (withValue == null)
            withValue = new Integer(0);

        if (withValue instanceof String || tmpVal instanceof String) {

            withValue = convertFromStringToNumber(withValue.toString());
            tmpVal = convertFromStringToNumber(tmpVal + "");
        }

        //compare double
        if (tmpVal instanceof Double || withValue instanceof Double) {
            Double cmpVal = (Double) tmpVal;
            Double cmpWithVal = (Double) withValue;

            return cmpVal.compareTo(cmpWithVal);
        } else if (tmpVal instanceof Integer || withValue instanceof Integer) {
            Integer cmpVal = (Integer) tmpVal;
            Integer cmpWithVal = (Integer) withValue;

            return cmpVal.compareTo(cmpWithVal);
        }

        return -1;

    }

    /*
     * Method abstraction of the arithmetic operation performed on a given
     * object set and the current value
     */
    private void arithmeticOperation(Object argument, ArithmeticOperation operation) {

        isValidClassNotNullClass(argument);
        Object tmpVal = value;

        if (argument == null)
            argument = new Integer(0);

        if (tmpVal == null)
            tmpVal = new Double(0);


        if (argument instanceof String || tmpVal instanceof String) {

            argument = convertFromStringToNumber(argument.toString());
            tmpVal = convertFromStringToNumber(tmpVal + "");

        }

        Double val1 = Double.parseDouble(tmpVal.toString());
        Double val2 = Double.parseDouble(argument.toString());

        if (argument instanceof Double || tmpVal instanceof Double) {

            value = Double.valueOf(operation.performeOperation(val1, val2));

        } else if (argument instanceof Integer || tmpVal instanceof Integer) {

            value = Integer.valueOf((int) operation.performeOperation(val1, val2));

        } else {
            throw new RuntimeException("Operation not permitted on " +
                    "un-supported types of data: " + argument);
        }


    }

    /**
     * Getter mathod for the value object
     *
     * @return Object representing the current object
     */
    public Object getValue() {
        return value;
    }

    /**
     * Setter for the current value object
     *
     * @param value Object to se set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Method converts a given String object to a Number object
     *
     * @param argument String to be tested
     * @return Number object representation
     */
    private Number convertFromStringToNumber(String argument) {
        String number = argument;
        Number num;

        if (number.contains(".") || number.contains("E")) {
            try {

                num = Double.parseDouble(number);

            } catch (NumberFormatException e) {
                throw new RuntimeException("Failed conversion on String to Double." +
                        " Value: " + number);
            }

        } else {
            try {

                num = Integer.parseInt(number);

            } catch (NumberFormatException e) {
                throw new RuntimeException("Failed conversion on String to Integer." +
                        " Value: " + number);
            }
        }

        return num;
    }

    /**
     * Method test if the object argument is a valid Object type
     *
     * @param value object to be tested
     * @return true if valid, false otherwise
     */
    private boolean isValidClassNotNullClass(Object value) {

        boolean valid = value instanceof Integer || value instanceof Double || value instanceof String;

        if (!valid)
            return false;

        return true;

    }

}
