package hr.fer.zemris.java.tecaj.hw07.shell.shellcommands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.EnvironmentUtil;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Cat shell command implementation class
 */
public class CatShellCommand implements ShellCommand {

    /**
     * Name of the shell command
     */
    private static final String COMMAND_NAME = "cat";

    /**
     * Description of the shell command
     */
    private static final String COMMAND_DESCRIPTION = "Shell command provides a was to read the textual" +
            "%contents of a file. Command accepts 1 or 2 arguments, if second is provided then it acts as a" +
            "%charset encoding after which the command will decode the byte flow - otherwise the system default is" +
            "used";

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        String[] argumentsSplit = EnvironmentUtil.checkInputArguments(env, arguments, COMMAND_NAME, 1, 2);

        if(argumentsSplit == null)
            return ShellStatus.CONTINUE;

        Path source = Paths.get(argumentsSplit[0]);
        Charset charset;


        if(!Files.exists(source)){
            EnvironmentUtil.writeMessage(env, "Requested file does not exist!");
            return ShellStatus.CONTINUE;
        }

        if(argumentsSplit.length == 1){
            charset = Charset.defaultCharset();
        } else {
            try {
                charset = Charset.forName(argumentsSplit[1]);
            } catch (IllegalCharsetNameException e){
                EnvironmentUtil.writeMessage(env, "Requested charset does not exist on your computer!");
                return ShellStatus.CONTINUE;
            }

        }

        printFile(source, charset, env);

        return ShellStatus.CONTINUE;
    }

    private void printFile(Path source, Charset charset, Environment env) {


        try(BufferedReader in  = new BufferedReader(new InputStreamReader(new FileInputStream(source.toFile()), charset))) {

            String str;

            while((str = in.readLine()) != null){
                env.write(str);
            }

            env.write("\n");

        } catch (FileNotFoundException e) {
            EnvironmentUtil.writeMessage(env, "File provided not found on the file system");
        } catch (IOException e) {
            EnvironmentUtil.writeMessage(env, "Error writing to the file");
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
