package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * Types of tokens that can be found in the specified 'programming' text
 */
public enum TokenType {
    /**
     * End of file representation
     */
    EOF,

    /**
     * Tag names, FOR, END special words
     */
    TAG,

    /**
     * Text type. Contains String information
     */
    TEXT,

    /**
     * Variable name, number, string, operator
     */
    ELEMENT,

    /**
     * Parsing control symbols - '{$' and '$}'
     */
    SPECIAL_SYMBOL;
}
