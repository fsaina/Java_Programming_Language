package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * General exception to be thrown if the format stored in the Database
 * is invalid
 */
public class DatabaseFileFormatException extends RuntimeException {

    /**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseFileFormatException(String s) {
        super(s);
    }

}
