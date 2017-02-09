package hr.fer.zemris.java.tecaj.hw07.shell.shellcommands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Exit command code implementation class
 */
public class ExitShellCommand implements ShellCommand {

    /**
     * Name of the shell command
     */
    private static final String COMMAND_NAME = "exit";

    /**
     * Description of the shell command
     */
    private static final String COMMAND_DESCRIPTION = "Shell command after which " +
            "all shell operations are terminated";

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        return ShellStatus.TERMINATE;
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
