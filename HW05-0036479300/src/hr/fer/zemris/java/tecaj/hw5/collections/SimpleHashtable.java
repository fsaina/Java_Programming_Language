package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple hash-table implementation of the data structure. Duplicate Value object are alowed as long there is
 * a different key object storing them. Same Key objects are not allowed.
 *
 * @param <K> Non null value to be used as a storing key
 * @param <V> Value to be stored with the Key
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

    /*
     * Def. size of the hashtable that is used in the constructor
     */
    private static final int DEFAULT_SIMPLEHASHTIBLE_SIZE = 16;
    /*
     * Overfilling treshhold for reallocation new elements
     */
    private static final double OVERFILLING_TRESHHOLD = 0.75;
    /**
     * Private reference to the table elements
     */
    private TableEntry<K, V>[] table;
    /**
     * Number of pairs stored in the data structure
     */
    private int size;
    /**
     * Number indicating the number of times the SimpleHashtable structure was changed.
     */
    private int modificationCount;

    /**
     * Default SimpleHashtable constructor
     */
    public SimpleHashtable() {
        this(DEFAULT_SIMPLEHASHTIBLE_SIZE);
    }

    /**
     * Alternative constructor that rounds the actual size to the first bigger power of two.
     *
     * @param capacity suggested size for the data object.
     */
    public SimpleHashtable(int capacity) {
        if (capacity < 1)
            throw new IllegalArgumentException("Size number given in constructor invalid: " + size);

        double power = Math.floor(Math.sqrt(capacity));
        int actualSize = (int) Math.pow(2, power);

        table = (TableEntry<K, V>[]) new TableEntry[actualSize];
        size = 0;

    }

    /**
     * Method updates a value with a key, if the key is already in the structure. Else a new element is added
     * on the list end. Two keys are equal when the method equals() determines so.
     *
     * @param key   key with wich a value will be stored
     * @param value value to store
     * @throws IllegalArgumentException when a null key object was passed
     */
    public void put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("Key values cannot be null, key passed: " + key);

        //assumed slot place
        int slotIndex = calculateSlotIndexFromKey(key);
        TableEntry<K, V> indexTableEntry = table[slotIndex];

        if (containsKey(key)) {
            //we know there is a key, so update the existing value

            while (!indexTableEntry.getKey().equals(key)) {
                indexTableEntry = indexTableEntry.next;
            }
            //set the item value as we have reached it
            indexTableEntry.setValue(value);

        } else {
            //add it as a last element in a list
            TableEntry<K, V> newEntry = new TableEntry<K, V>(key, value, null);

            if (indexTableEntry == null) {
                //add to the first element
                table[slotIndex] = newEntry;

            } else {

                while (indexTableEntry.next != null) {
                    indexTableEntry = indexTableEntry.next;
                }

                indexTableEntry.next = newEntry;
            }

            size++;
            modificationCount++;

            if (isOverfilling()) {

                K[] allCurrentKeys = getArrayOfAllKeys();
                V[] allCurrentValues = getArrayOfAllValues();

                resizeSimpleHastableStorage(table.length * 2);

                for (int i = 0; i < allCurrentKeys.length; i++) {
                    put(allCurrentKeys[i], allCurrentValues[i]);
                }

            }
        }
    }

    /*
     * Internal data structure resize method The method is destructive as
     * its clears all the existing data
     */
    private void resizeSimpleHastableStorage(int newSize) {
        clear();

        table = (TableEntry<K, V>[]) new TableEntry[newSize];
        size = 0;
    }

    /*
     * Returns all the values from the existing simpleHastable
     */
    private V[] getArrayOfAllValues() {

        V[] returnList = (V[]) new Object[size];
        int index = 0;

        for (TableEntry<K, V> entry : this) {
            returnList[index] = entry.getValue();
            index++;
        }

        return returnList;

    }

    /*
     * Returns all the keys from the existing simplehashtable
     */
    private K[] getArrayOfAllKeys() {
        K[] returnList = (K[]) new Object[size];
        int index = 0;

        for (TableEntry<K, V> entry : this) {
            returnList[index] = entry.getKey();
            index++;
        }

        return returnList;
    }

    /*
     * Private method for testing the overfitting parameter
     */
    private boolean isOverfilling() {
        if (size / (double) table.length >= OVERFILLING_TRESHHOLD)
            return true;
        return false;
    }

    /**
     * Method returns a value associated with the key object. If the key does not exist or the size of the
     * simple hashtable is 0, null reference is returned. Key objects equality is determined with the equals
     * method.
     *
     * @param key key object to be found.
     * @return Value object associated with the key
     */
    public V get(Object key) {
        if (key == null)
            throw new IllegalArgumentException("Requested a value with a key null.");

        for (TableEntry<K, V> entry : this) {
            if (entry.getKey().equals(key))
                return entry.getValue();
        }

        return null;

    }

    public int size() {
        return size;
    }

    /**
     * Method removes an occurrence of the key, value object pair in the data structure. If there is any object
     * with given key or a null reference was given as a key, the method does nothing. Otherwise a pair is
     * removed, de-referencing the key and value objects for the garbage collector.
     *
     * @param key key object remove with its value instance
     */
    public void remove(Object key) {

        if (key == null || size == 0 || !containsKey(key))
            return;

        //assumed slot place
        int slotIndex = calculateSlotIndexFromKey(key);
        TableEntry<K, V> indexTableEntry = table[slotIndex];

        if (indexTableEntry.getKey().equals(key)) {
            //the first element in list should be removed
            table[slotIndex] = indexTableEntry.next;
            indexTableEntry.key = null;
            indexTableEntry.value = null;

        } else {

            TableEntry<K, V> previousIndexTableElement = table[slotIndex];

            indexTableEntry = indexTableEntry.next;

            while (!indexTableEntry.getKey().equals(key)) {
                previousIndexTableElement = indexTableEntry;
                indexTableEntry = indexTableEntry.next;
            }

            previousIndexTableElement.next = indexTableEntry.next;
            indexTableEntry.key = null;
            indexTableEntry.value = null;
        }

        modificationCount++;
        size--;
    }

    /**
     * Method checks if or not there is a given key stored. Two keys are equal if their equals method
     * determines so.
     *
     * @param key Object to be looked for
     * @return true if contains, false otherwise
     */
    public boolean containsKey(Object key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null value");


        for (TableEntry<K, V> entry : this) {
            if (entry.getKey().equals(key))
                return true;
        }

        return false;
    }

    /**
     * Method checks if or not there is a given value stored. Values are equal if their equals method
     * determines so.
     *
     * @param value Object to be looked for as a value
     * @return true if contains, false otherwise
     */
    public boolean containsValue(Object value) {
        if (value == null)
            return false;

        for (TableEntry<K, V> entry : this) {
            if (entry.getValue().equals(value))
                return true;
        }

        return false;
    }

    /**
     * Method returns if the simple hashtable is empty or not
     *
     * @return true if is empty, false otherwise
     */
    public boolean isEmpty() {

        if (size != 0)
            return false;

        return true;
    }

    /**
     * Method removes all key value elements from the data structure
     */
    public void clear() {
        if (size == 0)
            return;

        for (int i = 0; i < table.length; i++) {
            //for each element in the hash table
            TableEntry<K, V> currentEntry = table[i];

            if (currentEntry == null)
                continue;

            TableEntry<K, V> previousReference;
            while (currentEntry != null) {
                //iterate trough the whole list
                currentEntry.key = null;
                currentEntry.value = null;
                previousReference = currentEntry;

                currentEntry = currentEntry.next;
                previousReference.next = null;

            }

        }

        modificationCount++;
        size = 0;
    }

    /**
     * Converts the whole Data structure into a String representation
     *
     * @return String with all the key:value's
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (TableEntry<K, V> currentEntry : this) {
            stringBuilder.append(currentEntry.toString());
            stringBuilder.append(", ");
        }

        if (stringBuilder.length() >= 2)
            stringBuilder.setLength(stringBuilder.length() - 2);

        stringBuilder.append(']');

        return stringBuilder.toString();
    }

    /*
     * Private internal method used for calculating the index of the slot to which
     * a value should be written. Key feature of the structure extracted to a standalone
     * method.
     */
    private int calculateSlotIndexFromKey(Object key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    /**
     * Method factory returns a new instance of the SimpleHashtable iterator - IteratorImpl object
     *
     * @return Iterator trough TableEntry classes it contains
     */
    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    /**
     * Private static table element representation class used for storing the elements
     *
     * @param <K> Object type to store keys
     * @param <V> Object type to store values
     */
    public static class TableEntry<K, V> {

        /**
         * key value object reference
         */
        private K key;

        /**
         * value object reference
         */
        private V value;

        /**
         * Reference to the next element in the list
         */
        private TableEntry<K, V> next;

        /**
         * Default constructor
         *
         * @param key       key element
         * @param value     value element
         * @param nextEntry reference to the next object
         */
        public TableEntry(K key, V value, TableEntry<K, V> nextEntry) {
            if (key == null)
                throw new IllegalArgumentException("Key value cannot be null");

            this.key = key;
            this.value = value;
            this.next = nextEntry;
        }

        /**
         * Getter for the key value
         *
         * @return key
         */
        public K getKey() {
            return key;
        }

        /**
         * Getter for the value
         *
         * @return value
         */
        public V getValue() {
            return value;
        }

        /**
         * Setter for the value type
         *
         * @param value to set this objects
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * Method override for returning the string representation
         *
         * @return String representing the data
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * Private class implementation of the Iterator interface for the SimpleHashtable.
     */
    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
        /**
         * Index of the current slot the iterator is located
         */
        int slotIndex = 0;
        /**
         * Int value storing the actual value on Object creation
         */
        private int modificationCountValueOnCreate = modificationCount;
        /**
         * Private variable storing reference to the current TableEntry
         */
        private TableEntry<K, V> currentPosition = table[slotIndex];

        /**
         * Reference to the last element returned
         */
        private TableEntry<K, V> lastReturnedElement;


        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            if (modificationCount != modificationCountValueOnCreate)
                throw new ConcurrentModificationException("The data structure was modified and the " +
                        "iterator does not represent the real state of internal structure");

            //hasNext method does not modify the currentPosition element
            TableEntry<K, V> returnElement = currentPosition;
            int tmpSlotIndex = slotIndex;

            while (returnElement == null) {
                tmpSlotIndex++;

                if (tmpSlotIndex == table.length)
                    break;

                returnElement = table[tmpSlotIndex];
            }

            if (returnElement != null)
                return true;

            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public TableEntry<K, V> next() {

            if (modificationCount != modificationCountValueOnCreate)
                throw new ConcurrentModificationException("The data structure was modified and the " +
                        "iterator does not represent the real state of internal structure");


            while (currentPosition == null) {
                slotIndex++;

                if (slotIndex >= table.length)
                    throw new NoSuchElementException("End of SimpleHashtable data structure reached");

                currentPosition = table[slotIndex];
            }

            lastReturnedElement = currentPosition;

            currentPosition = currentPosition.next;

            return lastReturnedElement;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (modificationCount != modificationCountValueOnCreate)
                throw new ConcurrentModificationException("The data structure was modified and the " +
                        "iterator does not represent the real state of internal stucuture");


            if (lastReturnedElement.getKey() == null)
                throw new IllegalStateException("Same object is being removed twice!");

            modificationCountValueOnCreate++;
            SimpleHashtable.this.remove(lastReturnedElement.getKey());

        }
    }
}