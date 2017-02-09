package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.*;

/**
 * Class implementation of a median retrieving array list
 *
 * @param <T> generics object type of a List
 */
public class LikeMedian<T extends Comparable<T>> implements Iterable<T> {

    /**
     * Reference to the internal data structure
     */
    private List<T> elements = new ArrayList<>();

    /**
     * Adds a element to the list
     *
     * @param element element to be added
     */
    public void add(T element) {
        if (element == null)
            throw new IllegalArgumentException("Passed argument cannot be null");

        elements.add(element);
    }

    /**
     * Returns the median element from the list
     *
     * @return Option objct with the median element
     */
    public Optional<T> get() {

        //before every get operation copy the list and sort it (alternative is to store a sorted list)
        List<T> tmpElements = new ArrayList<>(this.elements);
        Collections.sort(tmpElements);

        if (tmpElements.isEmpty()) {
            return Optional.of(null);
        }

        int median = (int) (((double) tmpElements.size() / 2.0));

        if (tmpElements.size() % 2 == 0) {
            return Optional.of(tmpElements.get(median - 1));
        }

        return Optional.of(tmpElements.get(median));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }
}
