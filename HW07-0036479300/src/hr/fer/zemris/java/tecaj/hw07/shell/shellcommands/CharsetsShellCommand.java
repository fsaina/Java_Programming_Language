package hr.fer.zemris.java.tecaj.hw07.shell.shellcommands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.EnvironmentUtil;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Charset shell command implementation
 */
public class CharsetsShellCommand implements ShellCommand {

    /**
     * Name of the shell command
     */
    private static final String COMMAND_NAME = "charsets";
    /**
     * Description of the shell command
     */
    private static final String COMMAND_DESCRIPTION = "Shell command prints out all " +
            "system supported charsets";

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        if (!arguments.isEmpty()) {
            EnvironmentUtil.writeMessage(env, "Wrong number of arguments for charsets. Non Required.");
            return ShellStatus.CONTINUE;
        }

        for(Charset charset : Charset.availableCharsets().values()){
            EnvironmentUtil.writeMessage(env, charset.toString());
        }

        return ShellStatus.CONTINUE;
    }

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
