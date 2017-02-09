package hr.fer.zemris.java.custom.collections;

/**
 * Custom Exception class that should be thrown when the ObjectStack Object(part of the Collections package)
 * is empty and it was accessed. This is a non-checked exception
 */
public class EmptyStackException extends RuntimeException {

    public EmptyStackException() {
        super();
    }

    public EmptyStackException(String message) {
        super(message);
    }
}
