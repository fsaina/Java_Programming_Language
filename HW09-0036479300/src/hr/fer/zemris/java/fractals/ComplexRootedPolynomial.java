package hr.fer.zemris.java.fractals;

import java.util.Arrays;
import java.util.List;

/**
 * Abstract class representation of the rooted polynomial
 */
public class ComplexRootedPolynomial {

    /**
     * Private list of root numbers
     */
    private final List<Complex> roots;


    /**
     * Default constructor
     *
     * @param roots array of complex numbers used
     */
    public ComplexRootedPolynomial(Complex... roots) {
        //TODO check null
        this.roots = Arrays.asList(roots);
    }

    /**
     * List of roots used in this class
     *
     * @return list of complex numbers used in this class
     */
    public List<Complex> getRoots() {
        return roots;
    }

    /**
     * Method applies the given complex number z to the root polynomial function
     *
     * @param z Complex number to be applied
     * @return Complex number result of operation
     */
    public Complex apply(Complex z) {
        Complex solution = z.sub(roots.get(roots.size() - 1));
        for (int i = roots.size() - 2; i >= 0; i--) {
            solution = solution.multiply(z.sub(roots.get(i)));
        }

        return solution;
    }


    /**
     * * Method converts this representation to ComplexPolynomial type
     *
     * @return ComplexPolynomial result of conversion
     */
    public ComplexPolynomial toComplexPolynom() {

        ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);

        for (Complex complex : roots) {
            result = result.multiply(new ComplexPolynomial(Complex.ONE, complex.negate()));
        }

        return result;

    }


    /**
     * Method converts the ComplexRootedPolynomial class to the String representation
     *
     * @return String representation of the Complexpolynomial
     */
    @Override
    public String toString() {
        return toComplexPolynom().toString();
    }

    /**
     * Method finds index of closest root for given complex number z that is within treshold if there is no
     * such root, returns -1
     *
     * @param z         Complex number for witch the nearest root will be found
     * @param threshold Threshold in witch the calculated value is considered valid
     * @return -1 if the closest root index is not found. Otherwise the index value incremented by 1 of the
     * position in the roots array
     */
    public int indexOfClosestRootFor(Complex z, double threshold) {

        double distanceMinimal = Double.MAX_VALUE;
        int indexOfTheSmallestDistance = -1;    //default value

        for (int i = 0; i < roots.size(); i++) {

            double dist = roots.get(i).sub(z).module();

            if (dist < threshold && dist < distanceMinimal) {
                indexOfTheSmallestDistance = i + 1;
                distanceMinimal = dist;
            }

        }
        return indexOfTheSmallestDistance;
    }

}