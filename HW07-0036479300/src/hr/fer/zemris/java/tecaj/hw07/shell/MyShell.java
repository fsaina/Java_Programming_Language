package hr.fer.zemris.java.tecaj.hw07.shell;

import hr.fer.zemris.java.tecaj.hw07.shell.shellcommands.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Class implementation of a basic java shell for interaction with the OS
 */
public class MyShell {

    /**
     * Shell version code
     */
    private static final String VERSION_CODE = "1.0";

    /**
     * Data structure containing all the command
     */
    private static Map<String, ShellCommand> commands;

    /**
     * Entry point of the application
     *
     * @param args list of input arguments (ignored)
     */
    public static void main(String[] args) {
        commands = new HashMap<>();
        //add commands
        commands.put("exit", new ExitShellCommand());
        commands.put("ls", new LsShellCommand());
        commands.put("symbol", new SymbolShellCommand());
        commands.put("tree", new TreeShellCommand());
        commands.put("copy", new CopyShellCommand());
        commands.put("mkdir", new MakeDirectoryShellCommand());
        commands.put("hexdump", new HexDumpShellCommand());
        commands.put("charsets", new CharsetsShellCommand());
        commands.put("cat", new CatShellCommand());
        commands.put("help", new HelpShellCommand());

        startShell();
    }

    /**
     * Method containing all thelogic for basic shell functions
     */
    public static void startShell() {
        Environment environment = new SimpleEnvironment(commands, '|', '>', '\\');
        ShellStatus status = ShellStatus.CONTINUE;

        EnvironmentUtil.writeMessage(environment, "Welcome to MyShell v" + VERSION_CODE);

        do {

            System.out.print(environment.getPromptSymbol() + " ");
            String line;

            Scanner inputReader = null;
            try {
                line = environment.readLine();
                inputReader = new Scanner(line);
            } catch (IOException e) {
                EnvironmentUtil.writeErrorMessage(environment, "Error reading from the standard environment");
                break;
            }

            String commandString;
            try {
                commandString = inputReader.next();
            }catch (NoSuchElementException e){
                EnvironmentUtil.writeMessage(environment, "Try again");
                continue;
            }
            String arguments = "";

            if (line.length() > commandString.length()) {
                arguments = line.substring(commandString.length() + 1);

                if (arguments.endsWith(environment.getMorelinesSymbol().toString())) {
                    arguments = EnvironmentUtil.parseMultiline(environment);
                }
            }

            ShellCommand command = commands.get(commandString);

            if (command == null) {
                EnvironmentUtil.writeMessage(environment, "Unsupported command input: " + commandString);
                continue;
            }

            status = command.executeCommand(environment, arguments);
            
            inputReader.close();

        } while (status != ShellStatus.TERMINATE);

    }

}
