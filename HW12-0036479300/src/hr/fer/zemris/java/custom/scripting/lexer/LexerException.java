package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * RuntimeException implementation for the Lexer class. The exception should be used only for Lexer class.
 */
public class LexerException extends RuntimeException {
    /**
	 * Serial UID
	 */
	private static final long serialVersionUID = -4409870663551641730L;

	public LexerException(String m) {
        super(m);
    }
}
