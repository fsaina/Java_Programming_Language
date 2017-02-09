package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Class implementation of the Instruction interface. Provides with the HALT operation.
 */
public class InstrHalt implements Instruction {

    /**
     * Default class constructor
     *
     * @param arguments list of parser-read arguments given to the command
     */
    public InstrHalt(List<InstructionArgument> arguments) {
        if (arguments.size() != 0) {
            throw new IllegalArgumentException("Expected 0 arguments!");
        }
    }

    /**
     * Execution method for this command. Called on start of the execution.
     *
     * @param computer reference to the computer that executes the command.
     * @return true if execution finished normally, false otherwise
     */
    @Override
    public boolean execute(Computer computer) {
        return true;
    }
}
