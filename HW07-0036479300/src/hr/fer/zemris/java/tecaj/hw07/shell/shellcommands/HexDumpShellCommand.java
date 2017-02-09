package hr.fer.zemris.java.tecaj.hw07.shell.shellcommands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.EnvironmentUtil;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * HexDump code implementation for the MyShell object class
 */
public class HexDumpShellCommand implements ShellCommand {

    /**
     * Name of the command
     */
    private static final String COMMAND_NAME = "hexdump";

    /**
     * Description of the shell command
     */
    private static final String COMMAND_DESCRIPTION = "Shell command provides easy way of requesting file" +
            " %hexadecimal writout.";

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String[] splitArguments = EnvironmentUtil.checkInputArguments(env, arguments, COMMAND_NAME, 1, 1);

        if (splitArguments == null)
            return ShellStatus.CONTINUE;

        Path input = Paths.get(splitArguments[0]);

        if (Files.isDirectory(input) || !Files.exists(input)) {
            EnvironmentUtil.writeMessage(env, "Provided path to a object is not to a file or does not exist!");
            return ShellStatus.CONTINUE;
        }

        String output = readFileBytes(input);

        EnvironmentUtil.writeMessage(env, output);

        return ShellStatus.CONTINUE;
    }

    /**
     * Private method reads input path two bytes by two bytes while generatin the use output. Method is
     * non-destructive.
     *
     * @param input Path to a file to be read
     * @return String representing the formated hexdump format
     */
    private String readFileBytes(Path input) {

        StringBuilder hexDumpFinalString = new StringBuilder();

        try (InputStream is = Files.newInputStream(input, StandardOpenOption.READ)) {

            byte[] buffer = new byte[16];

            int counter = 0;

            while (true) {

                int read = is.read(buffer);
                if (read < 1) break;

                StringBuilder line = new StringBuilder();

                counter = generateHexCounterString(buffer, counter, read, line);


                generateTextString(buffer, line);


                hexDumpFinalString.append(line.toString() + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return hexDumpFinalString.toString();
    }

    /**
     * Method generates a text string from given byte data to character data
     *
     * @param buffer byte array that represents the data to be conerted
     * @param line   String of converted byte field
     */
    private void generateTextString(byte[] buffer, StringBuilder line) {
        Charset charser = Charset.forName("UTF-8");

        line.append(byteToHex(buffer) + "| ");
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] < 32 || buffer[i] > 127) {
                buffer[i] = charser.encode(".").get(0);
            }
        }

        line.append(charser.decode(ByteBuffer.wrap(buffer)));
    }

    /*
     * Private class implementation that converts given decimal counter to a formatted hexadecimal
     * string representation
     */
    private int generateHexCounterString(byte[] buffer, int counter, int read, StringBuilder line) {
        line.append(String.format("%07X: ", counter));
        counter += read;
        return counter;
    }

    /**
     * Method used for converting byte array data to String of hexadecimal values
     *
     * @param data - data to be processed
     * @return String representing the processed data
     */
    private String byteToHex(byte[] data) {

        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02X ", b));
        }

        return sb.toString();
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
