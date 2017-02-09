package hr.fer.zemris.java.tecaj.hw07.shell.shellcommands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.EnvironmentUtil;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

/**
 * Tree shell command implementation for recursive filesystem printout
 */
public class TreeShellCommand implements ShellCommand {

    /**
     * Command name
     */
    private static final String COMMAND_NAME = "tree";

    /**
     * Description of the shell command
     */
    private static final String COMMAND_DESCRIPTION = "Shell command used for recursive print out of elements" +
            "%inside a filesystem tree structure";

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String[] argSplit = EnvironmentUtil.checkInputArguments(env, arguments, COMMAND_NAME, 1, 1);

        if (argSplit == null)
            return ShellStatus.CONTINUE;

        Path path = Paths.get(argSplit[0]);


        try {
            Files.walkFileTree(path, new Writer());
        } catch (IOException e) {
            EnvironmentUtil.writeErrorMessage(env, "Error while traveling trough the file tree");
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

    /**
     * Private Writer class implementation used for tree visiting over the directories
     */
    private static class Writer implements FileVisitor<Path> {

        /**
         * Curernt level within the data structure
         */
        private int level = 0;

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            writeOut(dir);
            level++;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            writeOut(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            writeOut(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            level--;
            return FileVisitResult.CONTINUE;
        }

        /**
         * Method used for writing formatted printout
         *
         * @param f path to the file currently beeing formatted
         */
        private void writeOut(Path f) {
            if (level == 0) {
                System.out.println(f.normalize().toAbsolutePath());
            } else {
                System.out.printf("%" + (2 * level) + "s%s%n", "", f.getFileName());
            }
        }

    }

}

