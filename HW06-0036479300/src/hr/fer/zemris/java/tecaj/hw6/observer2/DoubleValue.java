package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Observer class implementation for the IntegerStorage class. Class prints out the number stored in the
 * IntegerStorage class multiplied by two, but for n times - as declared in the constructor. On the n-th time
 * the observer is removed from the subject class.
 */
public class DoubleValue implements IntegerStorageObserver {

    /**
     * Private counter of times the Class will print out values
     */
    private int n;

    /**
     * Default constructor
     *
     * @param n number of times to print out values
     */
    public DoubleValue(int n) {
        this.n = n;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void valueChanged(IntegerStorageChange istorage) {
        n--;
        if (n >= 0) {
            System.out.println("Double value: " + 2 * istorage.getValueCurrentlyStored());
        } else {
            istorage.getIntegerStorage().removeObserver(this);
        }
    }
}
