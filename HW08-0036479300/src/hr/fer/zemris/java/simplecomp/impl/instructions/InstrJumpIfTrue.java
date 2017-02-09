package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Class implementation of the Instruction interface. Provides with the JUMPIFTRUE operation.
 */
public class InstrJumpIfTrue implements Instruction {

    /**
     * location to jump to if the req. is met
     */
    private int location;

    /**
     * Default class constructor
     *
     * @param arguments list of parser-read arguments given to the command
     */
    public InstrJumpIfTrue(List<InstructionArgument> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Expected 1 arguments!");
        }

        try {
            this.location = (Integer) arguments.get(0).getValue();
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Illegal argument type with jump command");
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
        if (computer.getRegisters().getFlag()) {
            computer.getRegisters().setProgramCounter(location);
        }
        return false;

    }
}

