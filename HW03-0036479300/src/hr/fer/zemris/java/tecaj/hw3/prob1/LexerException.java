package hr.fer.zemris.java.tecaj.hw3.prob1;


/**
 * RuntimeException implementation for the Lexer class. The exception should not be used anywhere else so it
 * was made package-private.
 */
public class LexerException extends RuntimeException {
    public LexerException(String s) {
        super(s);
    }
}
