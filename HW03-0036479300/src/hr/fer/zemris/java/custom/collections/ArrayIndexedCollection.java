package hr.fer.zemris.java.custom.collections;


/**
 * ArrayIndexedCollection data structure definition. In this data structure duplicate elements are allowed,
 * storage of null references is not allowed.
 *
 * @author Filip Saina
 */

public class ArrayIndexedCollection extends Collection {

    /**
     * Current number of elements in the data structure
     */
    private int size;

    /**
     * Current allocated size of the data structure
     */
    private int capacity;

    /**
     * Array containing the elements
     */
    private Object[] elements;

    /**
     * Construct a default data filed of capacity 16 with no elements
     */
    public ArrayIndexedCollection() {
        this(16);
    }

    /**
     * Construct the data object with the given capacity
     *
     * @param initialCapacity - non negative and non zero integer value
     */
    public ArrayIndexedCollection(int initialCapacity) {
        this(null, initialCapacity);
    }

    /**
     * Constructor a ArrayIndexedCollection from the given Collection object with all its elements
     *
     * @param collection Collection object to be copied, can be null
     */
    public ArrayIndexedCollection(Collection collection) {
        this(collection, collection.size());
    }

    /**
     * Create a ArrayIndexedCollection data structure from the given Collection object and the initial
     * capacity value
     *
     * @param collection      Collection object to be added, can be null reference
     * @param initialCapacity non negative and non zero integer value
     */
    public ArrayIndexedCollection(Collection collection, int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException("Initial capacity " +
                    "of ArrayIndexCollection must be a number greater than 0");
        this.elements = new Object[initialCapacity];
        this.capacity = initialCapacity;

        if (collection == null) return;

        for (Object o : collection.toArray()) {
            add(o);
        }
    }

    /**
     * Add the given object into this collection to the last place.
     *
     * @param value non null Object to be added
     * @throws IllegalArgumentException if null object is beeing added
     */
    public void add(Object value) {
        if (value == null)
            throw new IllegalArgumentException("Only non-null" +
                    " values can be added to the ArrayIndexedCollection");

        if (size == capacity) {
            /*create a new bigger data structure and copy all the
            elements to it*/
            Object[] futureStructure = new Object[size * 2];
            capacity = size * 2;

            int i = 0;
            for (Object element : elements) {
                futureStructure[i] = element;
                i++;
            }

            elements = futureStructure;
        }

        elements[size] = value;

        size++;
    }

    /**
     * Returns the Object at the given index from the collection
     *
     * @param index non nagative value smaller than the accual size of the collection
     * @return Object element at the required position
     *
     * @throws IllegalArgumentException if the index required is out of bounds - negative or equal or greater
     *                                  to size of the collection
     */
    public Object get(int index) {
        if (index < 0 || index > size - 1)
            throw new IllegalArgumentException("Requested index is not" +
                    "within the bounds of the data structure");

        return elements[index];
    }

    /**
     * Clears all the stored elements inside the collection. Method leaves the current capacity value as is.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Inserts the given value to the required position keeping the collection in the order it was prior the
     * insert
     *
     * @param value    Object element to be added to the collection
     * @param position integer value of the position to be added. Value must be between, and not including, 0
     *                 to size of the collection
     * @throws IllegalArgumentException when the given object was null or it was intened to be placed outside
     *                                  the scope of the data strucure
     */
    public void insert(Object value, int position) {
        if (position < 0 || position > size - 1)
            throw new IllegalArgumentException("Cant insert the element to a position outside the scope ");
        if (value == null)
            throw new IllegalArgumentException("Cant add a null element to the data structure");

        int index;

        add(value);     //adds the element to the end
        index = size - 1;

        //shift the last element to the required position
        while (index != position) {
            
            //bubble shift
            Object replacePointer = elements[index - 1];
            elements[index - 1] = elements[index];
            elements[index] = replacePointer;

            index--;
        }
    }

    /**
     * Method for determining the index value of the first occurance of the given Object.
     *
     * @param value Object element to be searched for in the collection. Can be null.
     * @return index value of the found Object within the collection
     */
    public int indexOf(Object value) {
        int i = 0;
        if (value == null) return -1;

        for (Object index : toArray()) {
            if (index.equals(value)) return i;
            i++;
        }
        return -1;
    }

    /**
     * Returns number of current elements in the collection
     *
     * @return current number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if, or not, the collection contains the given object value
     *
     * @param value Object element to be tested
     * @return true if contains, false in not contains
     */
    @Override
    public boolean contains(Object value) {
        if (indexOf(value) == -1) return false;
        return true;
    }

    /**
     * Method for removing the given element within the Array. The implementation removes a element at a given
     * position(null) and shifts is to the end -- out of scope of size.
     *
     * @param value Object to be removed
     * @return true if the Object is within the Array, false otherwise
     */
    @Override
    public boolean remove(Object value) {
        if (value == null) return false;

        int positionOfElement = indexOf(value);
        if (positionOfElement != -1) {
            remove(positionOfElement);
            return true;
        }
        return false;
    }

    /**
     * Method for removing the given element at at index within Array. The implementation removes a element at
     * a given position(null) and shifts is to the end -- out of scope of size.
     *
     * @param positionOfElement index of element to be removed
     */
    public void remove(int positionOfElement) {
        if (positionOfElement < 0 || positionOfElement > size - 1)
            throw new IllegalArgumentException("Cant remove an element outside the scope");

        elements[positionOfElement] = null;
        size--;

        int index = positionOfElement;
        //shift the null to the end
        while (index != size) {
            elements[index] = elements[index + 1];
            index++;
        }
        elements[index] = null;
    }

    /**
     * Method for returning the Object array of all its elements without the possible null values it can
     * contain by deleting elements from it
     *
     * @return Object array
     */
    @Override
    public Object[] toArray() {
        int index = 0;
        Object newArray[] = new Object[size];

        //populate the new array with all the elements
        for (int i = 0; i < size; i++) {
            if (elements[i] != null) {
                newArray[index] = elements[i];
                index++;
            }
        }

        return newArray;
    }
}
