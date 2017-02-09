package hr.fer.zemris.java.tecaj.hw07.shell.shellcommands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.EnvironmentUtil;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Class implementation of the symbol command. Current supported symbol operations are: PROMPT MORELINES
 * MULTILINE
 */
public class SymbolShellCommand implements ShellCommand {

    /**
     * Name of the command
     */
    private static final String COMMAND_NAME = "symbol";

    /**
     * Description of the shell command
     */
    private static final String COMMAND_DESCRIPTION = "Symbol shell command takes care over symbol control" +
            "in the shell. %Current supported symbol operations are: PROMPT MORELINES" +
            "MULTILINE";

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String[] argumentsStrings = arguments.split(" ");

        if (argumentsStrings.length < 1 || argumentsStrings.length > 2) {
            EnvironmentUtil.wrongNumberOfArguments(env, COMMAND_NAME);
            return ShellStatus.CONTINUE;
        }


        String symbol = argumentsStrings[0].trim().toUpperCase();
        Character oldValue = null;
        Character symbolValue = null;

        if (symbol.equals("PROMPT")) {
            if (argumentsStrings.length == 2) {
                oldValue = env.getPromptSymbol();
                env.setPromptSymbol(argumentsStrings[1].charAt(0));
            }
            symbolValue = env.getPromptSymbol();

        } else if (symbol.equals("MORELINES")) {
            if (argumentsStrings.length == 2) {
                oldValue = env.getMorelinesSymbol();
                env.setMorelinesSymbol(argumentsStrings[1].charAt(0));
            }
            symbolValue = env.getMorelinesSymbol();
        } else if (symbol.equals("MULTILINE")) {
            if (argumentsStrings.length == 2) {
                oldValue = env.getMultilineSymbol();
                env.setMultilineSymbol(argumentsStrings[1].charAt(0));
            }
            symbolValue = env.getMultilineSymbol();
        } else {
            EnvironmentUtil.writeMessage(env, "Unsupported symbol name requested: " + symbol);
        }

        if (argumentsStrings.length == 2) {
            EnvironmentUtil.writeMessage(env, "Symbol for " + symbol + " changed from '" + oldValue + "' to '" + argumentsStrings[1] + "'");
        } else {
            EnvironmentUtil.writeMessage(env, "Symbol for " + symbol + " is '" + symbolValue + "'");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getCommandDescription() {
        return Arrays.asList(COMMAND_DESCRIPTION.split("%"));
    }
}
