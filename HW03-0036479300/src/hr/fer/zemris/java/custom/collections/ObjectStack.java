package hr.fer.zemris.java.custom.collections;


/**
 * ObjectStack provides a adapter to for the Stack data structure functionality while using other data
 * structures of the Collection class.
 *
 * @author FIlip Saina
 */
public class ObjectStack {

    /**
     * Private data structure in which the data will be stored
     */
    private ArrayIndexedCollection array;

    /**
     * Default ObjectStack constructor initializes the data structure to be used
     */
    public ObjectStack() {
        array = new ArrayIndexedCollection();
    }

    /**
     * Check  or not the collection contains data
     *
     * @return true if its empty, false otherwise
     */
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * Current size of the data structure
     *
     * @return size of the data structure
     */
    public int size() {
        return array.size();
    }

    /**
     * Provides key functionalitie of a stack data model - push data on it.
     *
     * @param value Object to be pushed onto the stack
     * @throws IllegalArgumentException when null pointer is being pushed
     */
    public void push(Object value) {
        if (value == null)
            throw new IllegalArgumentException("Data structure - stack " +
                    "does not accept null value to be pushed");
        array.add(value);
    }

    /**
     * Provides key functionality of a stack data model - pop data from it.
     *
     * @return Object that was last added
     *
     * @throws EmptyStackException when popping an empty stack is made
     */
    public Object pop() {
        if (array.isEmpty() == true)
            throw new EmptyStackException("Popping a empty stack is not allowed");

        Object value = peek();   //get the last element
        array.remove(array.size() - 1);

        return value;
    }

    /**
     * Provides key functionalitie of a stack data model - peek data in it. That is, the returned value is not
     * removed from the stack.
     *
     * @return Object that was added
     *
     * @throws EmptyStackException whenn peeking an empty stack is made
     */
    public Object peek() {
        if (array.isEmpty() == true)
            throw new EmptyStackException("Peeking a empty stack is not allowed");
        int size = array.size();
        return array.get(size - 1);
    }

    /**
     * provides means to remove all objects from the stack
     */
    public void clear() {
        array.clear();
    }
}
