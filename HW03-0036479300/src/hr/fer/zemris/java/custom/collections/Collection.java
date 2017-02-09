package hr.fer.zemris.java.custom.collections;

/**
 * Collection is a class generalization of future extended data structure classes
 *
 * @author FIlip Saina
 */

class Collection {

    protected Collection() {
        //TODO implement
    }

    /**
     * Method to determine if the data structure is empty
     *
     * @return boolean - true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (size() == 0) return true;
        return false;
    }

    /**
     * Returns the number of currently stored objects in this collections.
     *
     * @return int - the size of occuying data fields
     */
    public int size() {
        return 0;
    }

    /**
     * Adds the given object into this collection.
     *
     * @param value Object to be added the the data structure
     */
    public void add(Object value) {
        //TODO implement
    }

    /**
     * Returns true only if the collection contains given value, as determined by equals method.
     *
     * @param value Object to be tested if is contained within the data structure
     * @return boolean - true if contains, false otherwise
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * Returns true only if the collection contains given value as determined by equals method and removes one
     * occurrence of it (in this class it is not specified which one).
     *
     * @param value Object to be removed from the data structure
     * @return
     */
    public boolean remove(Object value) {
        return false;
    }

    /**
     * Allocates new array with size equals to the size of this collections, fills it with collection content
     * and returns the array. This method never returns null.
     *
     * @return Object[] Array of elements from the data structure
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException("toArray() method is not supported in the Collection class");
    }

    /**
     * Method calls processor.process(.) for each element of this collection. The order in which elements will
     * be sent is undefined in this class.
     *
     * @param processor Object to perform processing over data structure
     */
    public void forEach(Processor processor) {
        for (Object element : toArray()) {
            processor.process(element);
        }
    }

    /**
     * Method adds into itself all elements from given collection. This other collection remains unchanged.
     * Implement it here to define a local processor class whose method process will add each item into
     * current collection by calling method add, and then call forEach on other collection with this processor
     * as argument. You can define this new class directly in method addAll: such classes are called local
     * classes.
     *
     * @param other Other Collection object from which the data will be copied from
     */
    public void addAll(Collection other) {
        Processor processor = new Processor();
        other.forEach(processor);
    }

    /**
     * Method for removing all the elements from the data structure.
     */
    void clear() {
        //TODO implement
    }
}
