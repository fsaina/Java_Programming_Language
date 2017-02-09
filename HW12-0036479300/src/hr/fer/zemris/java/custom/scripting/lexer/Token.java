package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * Token element. Data representation for the Lexer object to return after every iteration.
 */
public class Token {

    /**
     * Type of the data being stored in the token.
     */
    private TokenType type;

    /**
     * Value to be stored in the Token element
     */
    private Object value;

    /**
     * Token object constructor
     *
     * @param type  TokenType element to be stored
     * @param value Object element to be stored
     */
    public Token(TokenType type, Object value) {

        this.type = type;
        this.value = value;
    }

    /**
     * Getter for the Value element
     *
     * @return Object to be returned
     */
    public Object getValue() {
        return value;
    }

    /**
     * Getter fot the Type element
     *
     * @return TokenType of the token
     */
    public TokenType getType() {
        return type;
    }
}