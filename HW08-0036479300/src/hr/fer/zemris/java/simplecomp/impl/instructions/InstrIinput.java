package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;
import java.util.Scanner;

/**
 * Class implementation of the Instruction interface. Provides with the IINPUT operation.
 */
public class InstrIinput implements Instruction {

    /**
     * Reference to the memory location to be stored into
     */
    private int memLokacija;

    /**
     * Default class constructor
     *
     * @param arguments list of parser-read arguments given to the command
     */
    public InstrIinput(List<InstructionArgument> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Expected 1 argument!");
        }
        if (!arguments.get(0).isNumber()) {
            throw new IllegalArgumentException("Numeric value not given for memory location");
        }

        memLokacija = (Integer) arguments.get(0).getValue();

    }

    /**
     * Execution method for this command. Called on start of the execution.
     *
     * @param computer reference to the computer that executes the command.
     * @return true if execution finished normally, false otherwise
     */
    @Override
    public boolean execute(Computer computer) {

        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        Integer number;

        try {
            number = Integer.parseInt(line);
        } catch (Exception e) {
            computer.getRegisters().setFlag(false);
            return false;
        }

        computer.getMemory().setLocation(memLokacija, number);
        computer.getRegisters().setFlag(true);


        return false;
    }
}
