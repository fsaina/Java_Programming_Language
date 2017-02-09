package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Util class for the MyShell object implementation and the SimpleEnvironment standard input/output
 */
public class EnvironmentUtil {


    /**
     * Method writes a message to a given environment
     *
     * @param environment reference to a environment class
     * @param message     message to be written
     */
    public static void writeMessage(Environment environment, String message) {
        printStreamWriter(environment, message, System.out);
    }

    /**
     * Method appends a given message to a environment object output. No new line is added at the end
     *
     * @param environment reference to the environment class
     * @param message     String to be written
     */
    private static void appendMessage(Environment environment, String message) {
        try {
            environment.write(message);
        } catch (IOException e) {
            System.out.println("Error while writing to external enviroment");
            System.exit(-1);
        }
    }

    /**
     * Method used for writing to the environment class. Failing that, graceful fallback is made trough the
     * standard PrintStream interface
     *
     * @param environment reference to the environment class
     * @param message     String to be written on output
     * @param stream      printStream output location
     */
    private static void printStreamWriter(Environment environment, String message, PrintStream stream) {
        try {
            environment.writeln(message);
        } catch (IOException e) {
            stream.println("Error while writing to the external environment");
            System.exit(-1);
        }
    }

    /**
     * Method used for printing out a message referring the wrong number of argument provided
     *
     * @param env         environemtn class refeceren to which the message will be outputed
     * @param commandName String name of the command
     */
    public static void wrongNumberOfArguments(Environment env, String commandName) {
        writeMessage(env, "Wrong number of arguments provided to the >" + commandName + "< command");
    }

    /**
     * Method used for writing a error message to the environment output
     *
     * @param environment reference to the environment class
     * @param message     String to be outputted
     */
    public static void writeErrorMessage(Environment environment, String message) {
        printStreamWriter(environment, message, System.err);
    }

    /**
     * Method used for written a message to a standard output and reading input
     *
     * @param env reference to the environment class
     * @param s   String to be wirtten
     * @return response String from the user
     */
    public static String writeMessageAndReadUserInput(Environment env, String s) {
        writeMessage(env, s);
        appendMessage(env, env.getPromptSymbol() + " ");
        return parseMultiline(env);
    }

    /**
     * Method used for parsing the multiline user input
     *
     * @param environment reference to the environment class
     * @return String representation of the read arguments
     */
    public static String parseMultiline(Environment environment) {

        String line = null;
        StringBuilder buffer = new StringBuilder();
        Character moreLines = environment.getMorelinesSymbol();

        do {
            try {
                line = environment.readLine().trim();
            } catch (IOException e) {

                System.out.println("Error reading environment input!");
                System.exit(-1);

            }
            buffer.append(line);

        } while (line.endsWith(moreLines.toString()));

        String argumented = buffer.toString().replace(moreLines.toString(), "");

        return argumented.trim();
    }

    /**
     * Method check input arguments lenght and type and writes an appropriete message if needed
     *
     * @param env         environmnet
     * @param arguments   arguments string provided
     * @param commandName name of the command used
     * @param i           minimal number of arg
     * @param i1          maximal number of arguments
     * @return String array representing the arguments
     */
    public static String[] checkInputArguments(Environment env, String arguments, String commandName, int i, int i1) {

        String[] splitArguments;
        String[] split;

        if (arguments.contains("\"")) {
            split = arguments.split("\"");

        } else {
            split = arguments.trim().split(" ");
        }


        splitArguments = removeEmptyStrings(split);


        if (splitArguments.length < i || splitArguments.length > i1) {
            EnvironmentUtil.writeMessage(env, "Wrong number of arguments provided for " + commandName + " method");
            return null;
        }


        return splitArguments;
    }

    /**
     * Method removes all the empty strings form the string array
     *
     * @param split string array to be testes
     * @return strsing array withoud empty strings
     */
    private static String[] removeEmptyStrings(String[] split) {
        String[] splitArguments;
        int size = 0;
        for (String s : split) {
            if (!s.trim().isEmpty())
                size++;
        }

        splitArguments = new String[size];
        int a = 0;

        for (int n = 0; n < split.length; n++) {
            if (!split[n].trim().isEmpty()) {
                splitArguments[a] = split[n].trim();
                a++;
            }
        }
        return splitArguments;
    }
}
