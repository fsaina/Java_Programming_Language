package hr.fer.zemris.java.tecaj.hw07.shell.shellcommands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.EnvironmentUtil;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * List directory shell command implementation.
 * Command lists all files and direcorties in the current directory
 */
public class LsShellCommand implements ShellCommand {

    /**
     * Name of the command
     */
    private static final String COMMAND_NAME = "ls";

    /**
     * Date-time format used in the output formatting
     */
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * General ls output format used
     */
    private static final String STRING_OUTPUT_FORMAT = "%s %10d %s %s";

    /**
     * Description of the shell command
     */
    private static final String COMMAND_DESCRIPTION = "Shell command lists all of the files and directories " +
            "%located in the current directory.";

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String[] argumentsBlankSplit = EnvironmentUtil.checkInputArguments(env, arguments, COMMAND_NAME, 1, 1);

        if (argumentsBlankSplit == null)
            return ShellStatus.CONTINUE;


        Path path = Paths.get(argumentsBlankSplit[0]);

        if (!Files.exists(path)) {
            EnvironmentUtil.writeMessage(env, "Path provied does not exist, please provide a valid path");
            return ShellStatus.CONTINUE;
        }

        for (File file : path.toFile().listFiles()) {
            EnvironmentUtil.writeMessage(env, formatFileInformation(file.toPath()));
        }


        return ShellStatus.CONTINUE;
    }

    private String formatFileInformation(Path path) {

        String readWritePriv = fileReadWriteString(path);
        long sizeOfFile = 0;
        try {
            sizeOfFile = Files.size(path);
        } catch (IOException e) {
            System.err.println("Error while reading the size of file");
            System.exit(-1);
        }

        String creationDateTime = formatCreationDateTime(path);
        String fileName = path.toFile().getName();

        return String.format(STRING_OUTPUT_FORMAT,
                readWritePriv,
                sizeOfFile,
                creationDateTime,
                fileName);
    }

    private String formatCreationDateTime(Path path) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        BasicFileAttributeView faView = Files.getFileAttributeView(
                path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        BasicFileAttributes attributes = null;
        try {
            attributes = faView.readAttributes();
        } catch (IOException e) {
            System.err.println("Error reading attributed from the BasicFileAttributeClass");
            System.exit(-1);
        }
        FileTime fileTime = attributes.creationTime();
        String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
        return formattedDateTime;
    }

    private String fileReadWriteString(Path path) {
        StringBuilder sb = new StringBuilder();

        sb.append(testTermAndAppend(Files.isDirectory(path), 'd'));
        sb.append(testTermAndAppend(Files.isReadable(path), 'r'));
        sb.append(testTermAndAppend(Files.isWritable(path), 'w'));
        sb.append(testTermAndAppend(Files.isExecutable(path), 'x'));

        return sb.toString();

    }

    private char testTermAndAppend(boolean value, char ch) {
        if (value)
            return ch;
        return '-';
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
