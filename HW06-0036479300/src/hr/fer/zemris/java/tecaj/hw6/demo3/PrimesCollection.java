package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;

/**
 * Class provides a implementation for calculating the first i prime numbers
 */
public class PrimesCollection implements Iterable<Integer> {

    /**
     * Number of primes to calculate
     */
    private int numberOfPrimes;

    /**
     * Initial prime number to start calculating from
     */
    private static final int INITIAL_PRIME_NUMBER = 2;

    /**
     * Default Class constructor
     *
     * @param numberOfPrimes number of primes to calculate - natural number
     * @throws IllegalArgumentException if the given parameter is zero or negative
     */
    public PrimesCollection(int numberOfPrimes) {
        if (numberOfPrimes < 1)
            throw new IllegalArgumentException("Number of primes to calculate must be a natural number!");

        this.numberOfPrimes = numberOfPrimes;
    }

    /**
     * Factory method returns a instance of a prime number iterator class
     *
     * @return PrimesCollectionIterator class
     */
    @Override
    public Iterator<Integer> iterator() {
        return new PrimesCollectionIterator();
    }


    /*
     * Method calculates the i-th prime number. This is a implementation detail
     * so a public javadoc is not used.
     */
    private Integer getPrimeNumber(int nNumber) {

        int number = INITIAL_PRIME_NUMBER;
        int numberOfPrimes = 0;

        while (true) {

            boolean isPrime = true;
            for (int i = 2; i < number; i++) {
                if (number % i == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime)
                numberOfPrimes++;

            if (numberOfPrimes == nNumber)
                return number;

            number++;
        }
    }

    /**
     * Iterator class for the PrimesCollection class
     */
    public class PrimesCollectionIterator implements Iterator<Integer> {

        /**
         * Initial primesCalculated private field
         */
        private int primesCalculated = numberOfPrimes;

        /**
         * Method returns wether or not there are any more elements to calucate
         *
         * @return true if not all prime numbers are calcualated, false otherwise
         */
        @Override
        public boolean hasNext() {
            if (primesCalculated != 0) return true;
            return false;
        }

        /**
         * Method returns the next prime number of given primesCalculated
         *
         * @return next prime number
         */
        @Override
        public Integer next() {
            primesCalculated--;
            return PrimesCollection.this.getPrimeNumber(numberOfPrimes - primesCalculated);
        }
    }

}
