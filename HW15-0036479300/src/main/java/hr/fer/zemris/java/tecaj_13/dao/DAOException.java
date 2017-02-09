package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Exception class abstraction for the DAO implementations, thrown in case of error while executing the
 * request
 */
public class DAOException extends RuntimeException {

    /**
     * Serial version uid
     */
    private static final long serialVersionUID = 1L;

    /**
     * Exception constructor
     *
     * @param message message to be shown to the user
     * @param cause   cause explenation of the exception
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception constructor
     *
     * @param message message to be thrown
     */
    public DAOException(String message) {
        super(message);
    }
}