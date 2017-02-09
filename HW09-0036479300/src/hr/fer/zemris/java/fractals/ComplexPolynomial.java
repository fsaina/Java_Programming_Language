package hr.fer.zemris.java.fractals;

import java.util.Arrays;
import java.util.List;

/**
 * Complex number polynomial class representation
 */
public class ComplexPolynomial {

    /**
     * Private data store of the factors stored to make this polynomial
     */
    private final List<Complex> factors;

    /**
     * Default class constructor
     *
     * @param factors array of factors that make the polynomial
     */
    public ComplexPolynomial(Complex... factors) {
        this.factors = Arrays.asList(factors);
    }


    /**
     * Getter for the list of factors used in this class
     *
     * @return list of factos used
     */
    public List<Complex> getFactors() {
        return factors;
    }

    /**
     * Method return the order of the complex polynomial
     *
     * @return order of the complex polynomial
     */
    public short order() {
        return (short) (factors.size() - 1);
    }

    /**
     * Method calculated the polynomial value form this polynomial and the given one
     *
     * @param p given provied polynomail to be multiplied with
     * @return result of the complex number multiuplication
     */
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Complex[] factorsMultiplyed = new Complex[factors.size() + p.factors.size() - 1];

        for (int i = 0; i < factorsMultiplyed.length; i++) {
            factorsMultiplyed[i] = Complex.ZERO;
        }

        for (int i = 0; i < factors.size(); i++) {
            for (int j = 0; j < p.factors.size(); j++) {
                Complex multiplicationResult = factors.get(i).multiply(p.factors.get(j));
                factorsMultiplyed[i + j] = factorsMultiplyed[i + j].add(multiplicationResult);
            }
        }

        return new ComplexPolynomial(factorsMultiplyed);

    }

    /**
     * Method calculates the first derivation of the polynomial expression
     *
     * @return drived factors of the new overall derived polynomial function
     */
    public ComplexPolynomial derive() {

        // derivation == 0
        if (order() == 0) {
            return new ComplexPolynomial(Complex.ZERO);
        }

        Complex[] derivedFactors = new Complex[order()];

        for (int i = 0; i < order(); i++) {
            derivedFactors[i] = factors.get(i).multiply(new Complex(order() - i, 0));
        }

        return new ComplexPolynomial(derivedFactors);
    }

    /**
     * Method applies the given complex number to the polynomial expression that this object represents
     *
     * @param z complex number to be applied
     * @return Result of the operation in the Complex object form
     */
    public Complex apply(Complex z) {
        Complex exponent = Complex.ONE;
        Complex result = Complex.ZERO;

        for (int i = order(); i >= 0; i--) {
            result = result.add(factors.get(i).multiply(exponent));
            exponent = exponent.multiply(z);
        }

        return result;
    }

    /**
     * Method returns the calculated multiplication result of the polynomial in a complex format
     *
     * @return String representing the complex number result
     */
    @Override
    public String toString() {
        Complex number = apply(Complex.ONE);
        return number.toString();
    }
}