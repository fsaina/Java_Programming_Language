package hr.fer.zemris.java.tecaj.hw07.shell.shellcommands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.EnvironmentUtil;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * ShellCommand implementation of the copy command. Command takes two input paramteres and copies them to the
 * destination file.
 */
public class CopyShellCommand implements ShellCommand {

    /**
     * Name of the shell command
     */
    private static String COMMAND_NAME = "copy";

    /**
     * Description of the shell command
     */
    private static final String COMMAND_DESCRIPTION = "Shell command takes 2 arguments and copies the content" +
            "%of the source(1) file to the destination(2) file.";

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] splitArguments = EnvironmentUtil.checkInputArguments(env, arguments, COMMAND_NAME, 2, 2);

        if (splitArguments == null)
            return ShellStatus.CONTINUE;

        Path source = Paths.get(splitArguments[0]);
        Path destination = Paths.get(splitArguments[1]);


        if (Files.isDirectory(source) || !Files.exists(source)) {
            EnvironmentUtil.writeMessage(env, "Source path provided points to a directory or doesn't exist ");
            return ShellStatus.CONTINUE;
        }

        if (Files.exists(destination)) {
            String read = EnvironmentUtil.writeMessageAndReadUserInput(env, "File :" + destination.getFileName() + " already exists, would you like to overwrite it? (yes/no)");
            if (read.toLowerCase().equals("no")) {
                EnvironmentUtil.writeMessage(env, "Copying aborted!");
                return ShellStatus.CONTINUE;
            }

        }

        if (Files.isDirectory(destination)) {
            //copy there with same name
            destination = Paths.get(destination.toAbsolutePath().toString() + File.separatorChar + source.getFileName().toString());
        }

        Path parentDir = destination.toAbsolutePath().getParent();
        if (!Files.exists(parentDir))
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                System.err.println("Error creating the parent directory to local storage media");
                System.exit(-1);
            }


        performCopy(source, destination);

        return ShellStatus.CONTINUE;
    }


    private void performCopy(Path source, Path destination) {

        try (InputStream is = new BufferedInputStream(Files.newInputStream(source, StandardOpenOption.READ));
             OutputStream os = new BufferedOutputStream(Files.newOutputStream(destination, StandardOpenOption.CREATE))) {

            byte[] buffer = new byte[1024];
            while (true) {

                int read = is.read(buffer);
                if (read < 1)
                    break;

                os.write(buffer, 0, read);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

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
