package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;

/**
 * Public interface for every envirnment class implementation to oblige
 */
public interface Environment {
    /**
     * Method reads a line from the stream input
     *
     * @return String of the read line
     *
     * @throws IOException when there is a error in reading the line
     */
    String readLine() throws IOException;

    /**
     * Method writes to a given environment stream output
     *
     * @param text String to be written
     * @throws IOException thrown when there is a erron in readin or writing files
     */
    void write(String text) throws IOException;

    /**
     * Method writes a line(includin the new-line sign)
     *
     * @param text String to be written on the output
     * @throws IOException thrown when tehre is an error in wrinting to output
     */
    void writeln(String text) throws IOException;

    /**
     * Method returns a iterable data containing all the user command
     *
     * @return iterable<ShellCommand>>
     */
    Iterable<ShellCommand> commands();

    /**
     * Getter for the multiline character
     *
     * @return multinline characer char
     */
    Character getMultilineSymbol();

    /**
     * Setter for the multiline sharacter
     *
     * @param symbol character to be set
     */
    void setMultilineSymbol(Character symbol);

    /**
     * getter for the prompt symbol
     *
     * @return character representing the prompt symobl
     */
    Character getPromptSymbol();

    /**
     * setter for the prompt symbol
     *
     * @param symbol character to be set
     */
    void setPromptSymbol(Character symbol);

    /**
     * getter for the morelines symobl
     *
     * @return character representing the morelines symbol
     */
    Character getMorelinesSymbol();

    /**
     * settter for the morelines symbol
     *
     * @param symbol characer to be set as a new morelines symbol
     */
    void setMorelinesSymbol(Character symbol);

}
