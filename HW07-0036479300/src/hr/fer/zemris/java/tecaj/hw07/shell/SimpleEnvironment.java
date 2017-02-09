package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * Class implementation of the Environment interface providing basis user input/output capabilites to and from
 * the standard input output
 */
public class SimpleEnvironment implements Environment {

    /**
     * Iterable trought the ShellCommand data structures
     */
    private Iterable<ShellCommand> commandsIterable;

    /**
     * Multiline symbol character
     */
    private Character multilineSymbol;

    /**
     * promptSymbol character
     */
    private Character promptSymbol;

    /**
     * morelinesSymbol character
     */
    private Character morelinesSymbol;

    private Scanner read;

    /**
     * Default constructor of the environment
     *
     * @param shell           map of all shell commands
     * @param multilineSymbol character symbol of the multiline
     * @param promptSymbol    promt characer
     * @param morelinesSymbol morelines symbol to set
     */
    public SimpleEnvironment(Map<String, ShellCommand> shell, Character multilineSymbol, Character promptSymbol, Character morelinesSymbol) {
        this.commandsIterable = shell.values();
        this.multilineSymbol = multilineSymbol;
        this.promptSymbol = promptSymbol;
        this.morelinesSymbol = morelinesSymbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String readLine() throws IOException {
        if (read == null)
            read = new Scanner(System.in);
        return read.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(String text) throws IOException {
        System.out.print(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeln(String text) throws IOException {
        System.out.println(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<ShellCommand> commands() {
        return commandsIterable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getMultilineSymbol() {
        return multilineSymbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMultilineSymbol(Character symbol) {
        multilineSymbol = symbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPromptSymbol(Character symbol) {
        promptSymbol = symbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getMorelinesSymbol() {
        return morelinesSymbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMorelinesSymbol(Character symbol) {
        morelinesSymbol = symbol;
    }
}
