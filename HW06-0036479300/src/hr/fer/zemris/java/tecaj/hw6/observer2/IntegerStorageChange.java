package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Class representation of the internal storage
 */
public class IntegerStorageChange {

    /**
     * Reference to the interger storage class
     */
    private final IntegerStorage integerStorage;

    /**
     * Value before the change
     */
    private final int valueBeforeTheChange;

    /**
     * Value after the change
     */
    private final int valueCurrentlyStored;

    /**
     * Default class constructor
     *
     * @param integerStorage  reference to the integerStorage
     * @param beforeTheChange int before the change
     */
    public IntegerStorageChange(IntegerStorage integerStorage, int beforeTheChange) {

        this.integerStorage = integerStorage;
        this.valueBeforeTheChange = beforeTheChange;
        this.valueCurrentlyStored = integerStorage.getValue();

    }

    /**
     * Getter for the integer storage
     *
     * @return integer storage value
     */
    public IntegerStorage getIntegerStorage() {
        return integerStorage;
    }

    /**
     * Getter for the valueBeforeChange
     *
     * @return valueBeforaChange
     */
    public int getValueBeforeTheChange() {
        return valueBeforeTheChange;
    }

    /**
     * Getter for the value currently stored
     *
     * @return int value of the currently stored value
     */
    public int getValueCurrentlyStored() {
        return valueCurrentlyStored;
    }
}
