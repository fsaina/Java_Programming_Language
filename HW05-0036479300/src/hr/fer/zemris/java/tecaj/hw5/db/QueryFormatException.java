package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Exception class to be thrown when a error in parsing the
 * query occurs
 */
public class QueryFormatException extends RuntimeException {
    /**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public QueryFormatException(String s){
        super(s);
    }
}
