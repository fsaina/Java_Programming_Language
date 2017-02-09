package hr.fer.zemris.java.custom.scripting.parser;

/**
 * SmartScriptParser exception derived from the RuntimeException class Should be used only for the
 * SmartScriptParser
 */
public class SmartScriptParserException extends RuntimeException {
    /**
	 * Generated SvID
	 */
	private static final long serialVersionUID = -8076371261417023017L;

	SmartScriptParserException(String m) {
        super(m);
    }
}
