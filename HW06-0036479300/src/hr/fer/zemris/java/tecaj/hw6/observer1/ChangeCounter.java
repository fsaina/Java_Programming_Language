package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Observer class implementation for the IntegerStorage class. Class keeps tract of number of times it has
 * been called by the subject
 */
public class ChangeCounter implements IntegerStorageObserver {

    /**
     * Private counter value store
     */
    private int counter = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public void valueChanged(IntegerStorage istorage) {
        counter++;
        System.out.println("Number of value changes since tracking: " + counter);
    }
}
