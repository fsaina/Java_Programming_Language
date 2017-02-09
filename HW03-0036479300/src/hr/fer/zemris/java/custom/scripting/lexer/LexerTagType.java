package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum declaration of the TAG as provided in the text
 * This declaration was provided to remove the need for 'magic' declarations
 * and comparisons
 */
public enum LexerTagType {
    /**
     * Tag with the name FOR
     */
    FOR,

    /**
     * "No-name" TAG name
     */
    ECHO,

    /**
     * Tag with the name END
     */
    END;
}
