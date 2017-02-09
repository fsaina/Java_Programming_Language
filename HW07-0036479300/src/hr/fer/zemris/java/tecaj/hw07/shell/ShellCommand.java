package hr.fer.zemris.java.tecaj.hw07.shell;

import java.util.List;


/**
 * Interface of the ShellCommand abstraction that every shell command object must implement in order to be
 * applicable in the MyShell object
 */
public interface ShellCommand {
    /**
     * Method executes the implemented command
     *
     * @param env       environmnt variable of the implemented environment
     * @param arguments arguments required for operating the shell command
     * @return Shell status continue, false otherwise
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * Method returns the command name
     *
     * @return String object representing the name of the command
     */
    String getCommandName();

    /**
     * Method returns the command description containing all the viable information about the command
     *
     * @return information about the command
     */
    List<String> getCommandDescription();
}
