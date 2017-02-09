package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Memory implementation class
 */
public class MemoryImpl implements Memory {

    /**
     * Private storage of the Memory objects
     */
    private Object[] memory;

    /**
     * Default constructor
     *
     * @param size initial size of the memory implementation
     */
    public MemoryImpl(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size of memory cannot be 0 or negative");
        }
        memory = new Object[size];
    }

    /**
     * Setter for the given location
     *
     * @param location index of the location of the object inside the array
     * @param value    Object to be stored
     */
    @Override
    public void setLocation(int location, Object value) {
        if (location < 0 || location >= memory.length)
            throw new IndexOutOfBoundsException("Acessing non-existing memory location, value: " + location);

        memory[location] = value;

    }

    /**
     * Getter for a given location
     *
     * @param location int value of the position inside the array
     * @return Object value at a given position
     */
    @Override
    public Object getLocation(int location) {
        if (location < 0 || location >= memory.length)
            throw new IndexOutOfBoundsException("Acessing non-existing memory location, value: " + location);

        return memory[location];
    }
}
