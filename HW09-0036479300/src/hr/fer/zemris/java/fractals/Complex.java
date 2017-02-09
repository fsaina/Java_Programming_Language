package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;


/**
 * Abstract representation class of a complex number with basic operations provided
 */
public class Complex {

    /**
     * Real part of the irrational number
     */
    private final double re;

    /**
     * Imaginary part of the irrational number
     */
    private final double im;

    /**
     * Static constant for a comlex number with values 0 + i0
     */
    public static final Complex ZERO = new Complex(0, 0);

    /**
     * Static constant for a comlex number with values 1 + i0
     */
    public static final Complex ONE = new Complex(1, 0);

    /**
     * Static constant for a comlex number with values -1 + i0
     */
    public static final Complex ONE_NEG = new Complex(-1, 0);

    /**
     * Static constant for a comlex number with values 0 + i1
     */
    public static final Complex IM = new Complex(0, 1);

    /**
     * Static constant for a comlex number with values 0 - i1
     */
    public static final Complex IM_NEG = new Complex(0, -1);

    /**
     * Default class constructor. Initial values are 0 and 0
     */
    public Complex() {
        this(0, 0);
    }

    /**
     * Complex number class constructor with real and imaginary values provided
     *
     * @param re real part of a irrational number
     * @param im imaginary part of a irrational number
     */
    public Complex(double re, double im) {

        this.re = re;
        this.im = im;

    }

    /**
     * Getter for the real part of the number
     *
     * @return real part of a complex number
     */
    public double getReal() {
        return re;
    }

    /**
     * Getter for the imaginary part of the number
     *
     * @return imaginary part of a complex number
     */
    public double getImaginary() {
        return im;
    }

    /**
     * Method returns the module of a complex number
     *
     * @return complex number module
     */
    public double module() {
        return Math.sqrt(re * re + im * im);
    }

    /**
     * Method returns multiplication result of this number and a given complex number
     *
     * @param c complex number to be multiplied with
     * @return Complex number representation of the multiplication
     */
    public Complex multiply(Complex c) {
        return new Complex((re * c.re - im * c.im),
                (re * c.im + im * c.re));
    }

    /**
     * Method returns division result of this number and a given complex number
     *
     * @param c complex number to be divided with
     * @return Complex number representation of the division
     */
    public Complex divide(Complex c) {
        double realDivident = re * c.re + im * c.im;
        double imagDivident = im * c.re - re * c.im;

        double divisor = c.re * c.re + c.im * c.im;

        return new Complex(realDivident / divisor, imagDivident / divisor);
    }

    /**
     * Method performs the addition operation over this complex number and a given one
     *
     * @param c number to be added with
     * @return result of the operation in a complex number format
     */
    public Complex add(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    /**
     * Method performs the subtraction operation over this complex number and a given one
     *
     * @param c number to be subtracted with
     * @return result of the operation in a complex number format
     */
    public Complex sub(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }

    /**
     * Method performs the negation operation over this complex
     *
     * @return result of the operation in a complex number format
     */
    public Complex negate() {
        return new Complex(-re, -im);
    }

    /**
     * Method performs the power operation over this complex number and a given one
     *
     * @param n number to be powered with
     * @return result of the operation in a complex number format
     */
    public Complex power(int n) {
        return new Complex(Math.pow(module(), n) * (Math.cos(n * theta())),
                Math.pow(module(), n) * Math.sin(n * theta()));
    }

    /**
     * Method performs the root operation over this complex number and a given one
     *
     * @param n int number of the complex number root
     * @return list of roots
     */
    public List<Complex> root(int n) {

        List<Complex> soulutions = new ArrayList<Complex>();

        for (int k = 0; k < n; k++) {
            double argument = 2 * Math.PI * k / (double) n;
            soulutions.add(new Complex(Math.cos(argument), Math.sin(argument)));
        }

        return soulutions;

    }

    /**
     * Method for calculation the theta angle of the complex number
     *
     * @return double value of the angle
     */
    private double theta() {
        return Math.atan2(im, re);
    }

    /**
     * String representation of the complex number
     *
     * @return String value of a complex number
     */
    @Override
    public String toString() {
        final double ROUND_VALUE = 100.0;

        //TODO test this method

        double real = Math.round(re * ROUND_VALUE) / ROUND_VALUE;
        double imaginary = Math.round(im * ROUND_VALUE) / ROUND_VALUE;

        char sign = (im > 0) ? '+' : '-';
        return String.format("(%f ,%c i%f)", real, sign, Math.abs(imaginary));
    }

}