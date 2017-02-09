package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Observer class implementation for the IntegerStorage class.
 * Class is used to print out the square value of the current stored Integer istorege.
 */
public class SquareValue implements IntegerStorageObserver {

    /**
     * {@inheritDoc}
     */
    @Override
    public void valueChanged(IntegerStorage istorage) {
        System.out.println("Provided new value: " + istorage.getValue() + ", square is " + istorage.getValue()*istorage.getValue());
    }
}
