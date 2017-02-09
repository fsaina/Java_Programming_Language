package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Interface operation to implement by the ObjectMultistack arithmetic operation strategy
 */
public interface ArithmeticOperation {

    /**
     * Operation to be performed on two arguments
     *
     * @param argument1 first operand
     * @param argument2 second operand
     * @return result of the operation
     */
    double performeOperation(double argument1, double argument2);
}
