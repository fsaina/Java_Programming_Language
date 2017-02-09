package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Possible states of the lexer interpreter. For more information read the specification.
 */
public enum LexerState {
    /**
     * TEXT state for the Lexer state machine.
     */
    TEXT,

    /**
     * TAG state for the Lexer state machine
     */
    TAG;
}
