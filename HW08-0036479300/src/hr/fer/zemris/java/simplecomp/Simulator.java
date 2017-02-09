package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

import java.io.File;
import java.util.Scanner;


/**
 * Class simulator for the computer emulator used. If path to file is provided in the arguments list it is
 * used. Otherwise the used is asked to provided with a valid relative path to the program
 */
public class Simulator {


    /**
     * Entry point of the application
     *
     * @param args list of arguments. First argument , if provieded is used
     */
    public static void main(String[] args) {

        String programPath = null;

        if (args.length != 1) {
            programPath = askUserInput();
        } else {
            programPath = args[0];
        }

        File file = new File(programPath);
        if (!file.exists()) {
            System.err.println("The path provided to the file does not exist");
            System.exit(-1);
        }


        // Stvori računalo s 256 memorijskih lokacija i 16 registara
        Computer comp = new ComputerImpl(256, 16);

        // Stvori objekt koji zna stvarati primjerke instrukcija
        InstructionCreator creator = new InstructionCreatorImpl(
                "hr.fer.zemris.java.simplecomp.impl.instructions"
        );


        // Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
        // uporabom predanog objekta za stvaranje instrukcija
        try {
            ProgramParser.parse(
                    programPath, comp,
                    creator);
        } catch (Exception e) {
            System.err.println("Program provided has semantic/syntax errors in it");
            e.printStackTrace();
            System.exit(-1);
        }

        // Stvori izvršnu jedinicu
        ExecutionUnit exec = new ExecutionUnitImpl();

        // Izvedi program
        exec.go(comp);
    }

    /**
     * Method used for reading the user input from the standard input
     *
     * @return String represents one whole line read
     */
    private static String askUserInput() {
        System.out.println("Provide a relative path to the file containing the source code:\n> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
