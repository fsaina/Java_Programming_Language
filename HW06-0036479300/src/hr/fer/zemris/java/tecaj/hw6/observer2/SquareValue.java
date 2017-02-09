package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Observer class implementation for the IntegerStorage class.
 * Class is used to print out the square value of the current stored Integer istorege.
 */
public class SquareValue implements IntegerStorageObserver {

    /**
     * {@inheritDoc}
     */
    @Override
    public void valueChanged(IntegerStorageChange istorage) {
        int square = istorage.getValueCurrentlyStored()*istorage.getValueCurrentlyStored();
        System.out.println("Provided new value: " + istorage.getValueCurrentlyStored() + ", square is " + square);
    }
}
