package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Integer storage class implementation stores a single value
 */
public class IntegerStorage{

    /**
     * private integer of the stored value
     */
    private int value;

    /**
     * Reference to all observers currently subscribed to this subject object
     */
    private List<IntegerStorageObserver> observers;

    /**
     * Def. constructor for the IntegerStorage
     *
     * @param initialValue initial value to store
     */
    public IntegerStorage(int initialValue) {
        this.value = initialValue;
        observers = new ArrayList<IntegerStorageObserver>();
    }

    /**
     * Method adds a observer to a local list. Part of the observer pattern.
     *
     * @param observer Class to be added
     */
    public void addObserver(IntegerStorageObserver observer) {
        if (observer == null)
            throw new IllegalArgumentException("Observer class added cannot be a null value");

        // add the observer in observers if not already there
        if (observers.contains(observer) == false) {
            observers.add(observer);
        }
    }

    /**
     * Method removes a observer from a local list. Part of the observer pattern.
     *
     * @param observer Class to be removed
     */
    public void removeObserver(IntegerStorageObserver observer) {
        if (observer == null)
            throw new IllegalArgumentException("Objects to be removed cannot be null references");

        observers.remove(observer);
    }

    /**
     * Method clears all the subscribed observers from the subjects list
     */
    public void clearObservers() {
        observers.clear();
    }

    /**
     * Returns the current stored value
     *
     * @return int stored value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the current value while calling all the subscribed observers for additional actions to be made.
     *
     * @param value int value to be set
     */
    public void setValue(int value) {

        // Only if new value is different than the current value:
        if (this.value != value) {
            // Update current value
            this.value = value;
            // Notify all registered observers
            if (observers != null) {

                /*
                 The problem was with the DoubleValue class is that it wants, as a observer, remove itself from the
                 list that is currently being iterated --That threw the Modification exception. Now, with minimal
                 code modification we iterate trough a copy list and modify the list for the next run.
                 Alternatively we could have passed the Iterator reference to every IntegerStorageObserver instance
                 and expect the 'implementator' to used it accordingly.
                 */
                List<IntegerStorageObserver> copyOfObservers = new ArrayList<IntegerStorageObserver>(observers);

                IntegerStorageChange integerStorageChange = new IntegerStorageChange(this, value);

                for (IntegerStorageObserver observer : copyOfObservers) {
                    observer.valueChanged(integerStorageChange);
                }

            }
        }
    }

}
