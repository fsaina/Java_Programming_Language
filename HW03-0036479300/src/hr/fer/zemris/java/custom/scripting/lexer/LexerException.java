package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * RuntimeException implementation for the Lexer class. The exception should be used only for Lexer class.
 */
public class LexerException extends RuntimeException {
    public LexerException(String m) {
        super(m);
    }
}
