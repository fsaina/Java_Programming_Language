package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Lexer text processor class that implement basic tokanization
 *
 * @author Filip Saina
 */
public class Lexer {
    private char[] data;      // input text to be stored into
    private Token token;      // current token
    private int currentIndex; // index of the first unprocessed char
    private LexerState state;   //state of the lexer

    /**
     * Constructor for the Lexer class
     *
     * @param text String to be processed/tokenized
     */
    public Lexer(String text) {
        if (text == null)
            throw new IllegalArgumentException("Constructor parameter cannot be null");

        /*
         * Pre-processing step - replace all r,n,t occurances within then
         * remove all multiple whitespace characters to only one, trim
         * the whitespaces from the beginning and end of the string, as they
         * don't bring new information to the text processor.
         */

        text = text.replaceAll("\r", " ");
        text = text.replaceAll("\n", " ");
        text = text.replaceAll("\t", " ");
        text = text.replaceAll("\\s+", " ").trim();

        data = text.toCharArray();
        currentIndex = 0;
        state = LexerState.BASIC;
    }

    /**
     * Generates and returns the next token
     *
     * @return Token of the next element in the string
     *
     * @throws LexerException When there is a new token request after the EOF token
     * @throws LexerException Invalid backlash at the end of the input string
     * @throws LexerException Invalid type of data after backslash
     * @throws LexerException Number inputted too big for Long data type
     */
    public Token nextToken() {

        TokenType nextType = null;  //type of the returning Token
        Object nextValue = null;    //value of the returning Token

        /*
         * Flag that is used for determine a special state after a backslash
         * character is found within the input
         */
        boolean backslash = false;

        //if a request is made for a next Token after the EOF is reached, an exception occurs
        if (token != null && token.getType() == TokenType.EOF)
            throw new LexerException("End-of-file already reached!");

        if (data.length == currentIndex) {
            token = new Token(TokenType.EOF, null);
            return token;
        }

        /*
         * Skip the whitespace as it is ignored.
         * It is guaranteed that only one whitespace will be to skip due to the
         * pre-processing in the constructor
         */
        if (Character.isWhitespace(data[currentIndex])) currentIndex++;

        if (data[currentIndex] == '\\' && state == LexerState.BASIC) {
            backslash = true;
            currentIndex++;


            if (currentIndex == data.length)
                throw new LexerException("Invalid backslash char at the end");

            //not a digit or not another backslash is not a accepted state
            if (!(Character.isDigit(data[currentIndex]) || data[currentIndex] == '\\')) {
                throw new LexerException("Only accepted type after a backslash " +
                        "is a number or another backlash");
            }
        }

        if (state == LexerState.EXTENDED) {

            StringBuilder stringBuilder = new StringBuilder();

            if (data[currentIndex] == '#') {
                //our special symbol
                nextType = TokenType.SYMBOL;
                nextValue = new Character('#');
                currentIndex++;
            } else {
                //it's a word
                while (data[currentIndex] != ' ' && data[currentIndex] != '#') {
                    stringBuilder.append(data[currentIndex]);
                    currentIndex++;
                    if (currentIndex == data.length) break;
                }

                nextType = TokenType.WORD;
                nextValue = stringBuilder.toString();
            }

        } else if (state == LexerState.BASIC) {

            //tests if it is a letter, number or symbol
            if (Character.isLetter(data[currentIndex]) || backslash) {
                nextType = TokenType.WORD;
                StringBuilder stringBuilder = new StringBuilder();

                while (Character.isLetter(data[currentIndex]) || backslash) {
                    backslash = false;

                    stringBuilder.append(data[currentIndex]);
                    currentIndex++;

                    if (currentIndex == data.length) break;

                    if (data[currentIndex] == '\\') {
                        currentIndex++;
                        backslash = true;
                    }
                }

                nextValue = stringBuilder.toString();

            } else if (Character.isDigit(data[currentIndex])) {
                nextType = TokenType.NUMBER;
                StringBuilder stringBuilder = new StringBuilder();

                while (Character.isDigit(data[currentIndex])) {
                    stringBuilder.append(data[currentIndex]);
                    currentIndex++;

                    if (currentIndex == data.length) break;
                }

                //if the number is too long, cascade the exception
                try {
                    nextValue = Long.parseLong(stringBuilder.toString());
                } catch (NumberFormatException e) {
                    throw new LexerException("Number too big to be processed");
                }
            } else {
                //if it's not word, number or a blank space then it's a symbol
                nextType = TokenType.SYMBOL;
                nextValue = Character.valueOf(data[currentIndex]);
                currentIndex++;
            }
        }

        token = new Token(nextType, nextValue);
        return token;
    }

    /**
     * Returns the last generated token. Can be called multiple times. Does not start the generation of the
     * next Token.
     *
     * @return Last generated Token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Set the read state of the Lexer object
     *
     * @param state LexerState value
     */
    public void setState(LexerState state) {
        if (state == null)
            throw new IllegalArgumentException("State can not be set to null");
        this.state = state;
    }
}
