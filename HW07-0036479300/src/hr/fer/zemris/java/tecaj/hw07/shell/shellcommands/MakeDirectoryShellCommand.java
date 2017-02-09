package hr.fer.zemris.java.tecaj.hw07.shell.shellcommands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.EnvironmentUtil;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Mkdir class implementation
 */
public class MakeDirectoryShellCommand implements ShellCommand {

    /**
     * Name of the command
     */
    private static final String COMMAND_NAME = "mkdir";

    /**
     * Description of the shell command
     */
    private static final String COMMAND_DESCRIPTION = "Shell command provides direcorty with subdirectory making" +
            "%functionality.";

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String[] splitArguments = EnvironmentUtil.checkInputArguments(env, arguments, COMMAND_NAME, 1, 1);

        if (splitArguments == null)
            return ShellStatus.CONTINUE;

        Path destination = Paths.get(splitArguments[0]);

        if (Files.exists(destination)) {
            EnvironmentUtil.writeMessage(env, "Directory: " + destination.getFileName() + " already exists");
            return ShellStatus.CONTINUE;
        }

        try {
            Files.createDirectories(destination);
        } catch (IOException e) {
            EnvironmentUtil.writeErrorMessage(env, "Cannot write a directory: " + destination.getFileName());
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
