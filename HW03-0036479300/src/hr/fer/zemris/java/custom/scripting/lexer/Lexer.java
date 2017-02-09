package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.tecaj.hw3.prob1.LexerException;

import java.util.regex.Pattern;

/**
 * Lexer class that processes the input text and used lazy document interpretation. Lexer obliges the
 * specification written in the third java homework.
 *
 * @author Filip Saina
 */
public class Lexer {

    private char[] data;        // input text to be stored into
    private Token token;        // current token
    private int currentIndex;   // index of the first unprocessed char
    private LexerState state;   // state of the lexer

    /**
     * Lexer constructor, takes the string to be processed as a argument. Throws appropriete exception if
     * null. Initial lexer state is TEXT
     *
     * @param text
     */
    public Lexer(String text) {
        if (text == null)
            throw new IllegalArgumentException("Input for the Lexer constructor can't be null");

        data = text.toCharArray();
        currentIndex = 0;

        //Initial state of the lexer
        state = LexerState.TEXT;
    }

    /**
     * NextToken method processor. Lexer is implemented as a lazy string processor, therefore token is
     * generated on request. Every classification is returned in form of a Token element. Processed text
     * should oblige to some specific terms like: '{$', '$}' are special characters and should come in
     * open/close pairs without whitespaces between them. For more information refer to the third java
     * assignment specification.
     *
     * @return Token representation of the element processed
     *
     * @throws LexerException When the document is not formatted by the specification or its being called
     *                        after a EOF Token was returned.
     */

    public Token nextToken() {

        TokenType nextType = null;  //type of the returning Token
        Object nextValue = null;    //value of the returning Token

        //if a request is made for a next Token after the EOF is reached, an exception occurs
        if (token != null && token.getType() == TokenType.EOF)
            throw new LexerException("End-of-file already reached!");

        if (data.length == currentIndex) {
            token = new Token(TokenType.EOF, null);
            return token;
        }

        //don't process a whitespace
        while (Character.isWhitespace(data[currentIndex])) currentIndex++;

        if (state == LexerState.TEXT) {
            //process TEXT and special sign like {$

            StringBuilder stringBuilder = new StringBuilder();

            if (data[currentIndex] == '{') {
                //Processing a symbol

                currentIndex++;
                if (currentIndex == data.length)
                    throw new LexerException("Illegal format of the document. Ending with {");

                if (data[currentIndex] == '$') {

                    nextType = TokenType.SPECIAL_SYMBOL;
                    nextValue = LexerSpecialSymbol.OPEN_SYMBOL;
                    currentIndex++;

                } else {
                    throw new LexerException("Illegal word start with a '{' sign, after that sign a '$' must occur");
                }

            } else {
                //processing TEXT

                //get all text until { or end of file
                while (data[currentIndex] != '{') {

                    if (data[currentIndex] == '\\') {
                        //backslash in TEXT mode
                        currentIndex++;

                        if (currentIndex == data.length)
                            throw new LexerException("Illegal format of the document. Ending with \\");

                        if (data[currentIndex] == '\\') {
                            //ignore
                        } else if (data[currentIndex] == 'n' || data[currentIndex] == 'r' || data[currentIndex] == 't') {
                            stringBuilder.append("\\");
                        } else if (data[currentIndex] == '{') {
                            break;
                        } else {
                            throw new LexerException("Illegal use of backslashes");
                        }
                    }

                    stringBuilder.append(data[currentIndex]);
                    currentIndex++;
                    if (currentIndex == data.length) break;
                }

                nextType = TokenType.TEXT;
                nextValue = stringBuilder.toString();
            }

        } else if (state == LexerState.TAG) {
            //processing a TAG

            if (data[currentIndex] == '$') {
                //our last special symbol $}

                currentIndex++;
                if (currentIndex == data.length)
                    throw new LexerException("Illegal format of the document. Ending with $");

                if (data[currentIndex] == '}') {

                    nextType = TokenType.SPECIAL_SYMBOL;
                    nextValue = LexerSpecialSymbol.CLOSE_SYMBOL;
                    currentIndex++;

                } else {
                    throw new LexerException("Illegal use of '$' char, after that char a '}' must occur");
                }


            } else {
                //processing a ELEMENT

                StringBuilder stringBuilder = new StringBuilder();

                if (data[currentIndex] == ' ') {
                    //skip it

                    currentIndex++;

                } else if (data[currentIndex] == '=') {
                    //no-name tag name

                    nextType = TokenType.TAG;
                    nextValue = LexerTagType.ECHO;
                    currentIndex++;

                } else if (Character.isLetter(data[currentIndex]) && token.getType() == TokenType.SPECIAL_SYMBOL) {
                    //a tag name

                    stringBuilder.append(data[currentIndex]);
                    currentIndex++;

                    //as long the next characters are letters, digits, underscores
                    while (isValidVarChar(data[currentIndex])) {
                        stringBuilder.append(data[currentIndex]);
                        currentIndex++;
                    }

                    String tagName = stringBuilder.toString();

                    if ("END".equals(tagName.toUpperCase())) {

                        nextType = TokenType.TAG;
                        nextValue = LexerTagType.END;

                    } else if ("FOR".equals(tagName.toUpperCase())) {

                        nextType = TokenType.TAG;
                        nextValue = LexerTagType.FOR;

                    } else {

                        nextType = TokenType.TAG;
                        nextValue = new ElementVariable(tagName);

                    }


                } else if (Character.isLetter(data[currentIndex])) {
                    //its a variable name

                    stringBuilder.append(data[currentIndex]);
                    currentIndex++;

                    //as long the next characters are letters, digits, underscores
                    while (isValidVarChar(data[currentIndex])) {
                        stringBuilder.append(data[currentIndex]);
                        currentIndex++;
                    }

                    nextType = TokenType.ELEMENT;
                    nextValue = new ElementVariable(stringBuilder.toString());

                } else if (isValidOperatorChar(data[currentIndex]) && !Character.isDigit(data[currentIndex + 1])) {
                    //We are allowed to test the next element up ahead for the reason we are in a tag there the operator
                    //is bound no to be last element or similar

                    //its an operator ( *+-/^ )

                    nextType = TokenType.ELEMENT;
                    nextValue = new ElementOperator(String.valueOf(data[currentIndex]));
                    currentIndex++;

                } else if (data[currentIndex] == '@') {
                    //its a function that starts with @
                    currentIndex++;

                    if (Character.isLetter(data[currentIndex])) {

                        stringBuilder.append(data[currentIndex]);
                        currentIndex++;

                        //as long the next characters are letters, digits, underscores
                        while (isValidVarChar(data[currentIndex])) {
                            stringBuilder.append(data[currentIndex]);
                            currentIndex++;
                        }

                    } else {
                        throw new LexerException("Invalid function name");
                    }

                    nextType = TokenType.ELEMENT;
                    nextValue = new ElementFunction(stringBuilder.toString());

                } else if (data[currentIndex] == '"') {
                    //it starts with >"<...should parse up to another >"<

                    currentIndex++;

                    //until another " character is found, read data
                    while (!('"' == data[currentIndex])) {

                        //whole document is reached and there is no closing >"< character
                        if (data.length == currentIndex)
                            throw new LexerException("No string closing character reached, invalid format!");

                        if (data[currentIndex] == '\\') {
                            //a backslash
                            currentIndex++;

                            if (currentIndex == data.length)
                                throw new LexerException("Illegal format of the document. Ending with \\");

                            if (data[currentIndex] == '\\') {
                                //ignore
                            } else if (data[currentIndex] == '"') {
                                //ignore
                            } else if (data[currentIndex] == 'n' || data[currentIndex] == 'r' || data[currentIndex] == 't') {
                                stringBuilder.append('\\');
                            } else {
                                throw new LexerException("Illegal character after \\ in a string");
                            }
                        }

                        stringBuilder.append(data[currentIndex]);
                        currentIndex++;

                    }

                    nextType = TokenType.ELEMENT;
                    nextValue = new ElementString(stringBuilder.toString());
                    currentIndex++;

                } else if (Character.isDigit(data[currentIndex]) || isValidNumberPrefix(data[currentIndex])) {
                    //its either a decimal or a whole number
                    //starts with [0-9] + -

                    String numberString;
                    stringBuilder.append(data[currentIndex]);
                    currentIndex++;

                    //read all digits that represent the number
                    while (isDigitOrDot(data[currentIndex])) {
                        stringBuilder.append(data[currentIndex]);
                        currentIndex++;
                    }

                    numberString = stringBuilder.toString();

                    if (numberString.contains(".")) {
                        //process a Double

                        Double number;

                        try {
                            number = Double.parseDouble(numberString);
                        } catch (NumberFormatException e) {
                            throw new LexerException("Unable to parse Double number");
                        }

                        nextType = TokenType.ELEMENT;
                        nextValue = new ElementConstantDouble(number);

                    } else {
                        //process a whole number

                        Integer number;

                        try {
                            number = Integer.parseInt(numberString);
                        } catch (Exception e) {
                            throw new LexerException("Unable to parse Integer number");
                        }

                        nextType = TokenType.ELEMENT;
                        nextValue = new ElementConstantInteger(number);
                    }

                    //currentIndex++;
                } else {

                    /*
                     * Processed all types of possible TAG options,
                     * non of them match
                     */
                    throw new LexerException("Invalid parsing syntax");
                }
            }
        }

        token = new Token(nextType, nextValue);
        return token;
    }


    /*
     * Private method for matching possible number prefixes in text like the plus or minus sign
     */
    private boolean isValidNumberPrefix(char c) {
        return Pattern.matches("[+\\-]", String.valueOf(c));
    }

    /*
     * Private method for processing characters thar are either digits representations
     * or delimeter character representations
     *
     * @return boolean true if valid, false otherwise
     */

    private boolean isDigitOrDot(char c) {
        return (Character.isDigit(c) || ".".equals(c));
    }

    /*
     * Private method for matching possible operator characters
     *
     * @return boolean true if valid, false otherwise
     */
    private boolean isValidOperatorChar(char c) {
        return Pattern.matches("[+\\-*\\/^]", String.valueOf(c));
    }

    /*
     * Private method for processing a character that should be a letter(case insensitive),
     * digit or an underscore
     *
     * @return boolean true if valid, false otherwise
     */
    private boolean isValidVarChar(char c) {
        return Pattern.matches("\\w*", String.valueOf(c));
    }

    /**
     * Method returns the last processed Token element. If no elements are processed a null reference is
     * returned
     *
     * @return Token representing the last processed element
     */
    public Token getToken() {
        return token;
    }

    /**
     * Method sets the state in which the Lexer is currently processing. Null values are not permitted.
     *
     * @param state LexerState enum
     */
    public void setState(LexerState state) {
        if (state == null)
            throw new IllegalArgumentException("Lexer can't be set to null");
        this.state = state;
    }
}
