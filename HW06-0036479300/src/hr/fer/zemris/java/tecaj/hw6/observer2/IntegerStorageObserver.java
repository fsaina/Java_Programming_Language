package hr.fer.zemris.java.tecaj.hw6.observer2;


/**
 * Observer contract implementation method required by the subject class - IntegerStorage.
 */
public interface IntegerStorageObserver {

    /**
     * Observer contract implementation method required by the subject class. Should perform an operation over
     * istorage provided values
     *
     * @param istorage reference to the subject class
     */
    public void valueChanged(IntegerStorageChange istorage);

}
