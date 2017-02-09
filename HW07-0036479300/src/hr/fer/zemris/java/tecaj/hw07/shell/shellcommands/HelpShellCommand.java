package hr.fer.zemris.java.tecaj.hw07.shell.shellcommands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.EnvironmentUtil;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Shell command implementation class providing all logic for the help shell command
 */
public class HelpShellCommand implements ShellCommand {

    /**
     * Name of the command
     */
    private static final String COMMAND_NAME = "help";

    /**
     * Description of the shell command
     */
    private static final String COMMAND_DESCRIPTION = "Shell command, if no agruments are provided," +
            "%prints out all the command available in the shell. Otherwise a selective command description" +
            "%is shown to the user.";

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String[] split = EnvironmentUtil.checkInputArguments(env, arguments, COMMAND_NAME, 0, 1);

        if (split == null)
            return ShellStatus.CONTINUE;

        if (split.length == 0) {

            printOutAllCommandsInThisEnv(env);

        } else {

            printOutCommandInfo(env, split[0]);
        }


        return ShellStatus.CONTINUE;
    }

    private void printOutCommandInfo(Environment env, String s) {

        ShellCommand command = null;

        for (ShellCommand comm : env.commands()) {
            if (comm.getCommandName().equals(s)) {
                command = comm;
                break;
            }
        }

        if (command == null) {
            EnvironmentUtil.writeMessage(env, "No help found for command with the name: " + s);
            return;
        }

        printoutCommand(env, command);

    }

    private void printoutCommand(Environment env, ShellCommand command) {

        EnvironmentUtil.writeMessage(env, "COMMAND NAME: " + command.getCommandName());
        for (String desc : command.getCommandDescription()) {
            EnvironmentUtil.writeMessage(env, "| " + desc);
        }

    }

    private void printOutAllCommandsInThisEnv(Environment env) {


        for (ShellCommand command : env.commands()) {
            try {
                env.write(command.getCommandName() + "\n");
            } catch (IOException e) {
                System.err.println("Error writing to environment output!");
                break;
            }
        }

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
