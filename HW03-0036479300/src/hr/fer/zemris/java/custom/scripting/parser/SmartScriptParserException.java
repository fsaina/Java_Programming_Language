package hr.fer.zemris.java.custom.scripting.parser;

/**
 * SmartScriptParser exception derived from the RuntimeException class Should be used only for the
 * SmartScriptParser
 */
public class SmartScriptParserException extends RuntimeException {
    SmartScriptParserException(String m) {
        super(m);
    }
}
