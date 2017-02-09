package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Types of values a Token can obtain
 */
public enum TokenType {

    /**
     * End-of-file representation
     */
    EOF,

    /**
     * Word element containing a String
     */
    WORD,

    /**
     * Number/digit element, contains numeric data
     */
    NUMBER,

    /**
     * Special symobol in the syntax of the text
     */
    SYMBOL
}
