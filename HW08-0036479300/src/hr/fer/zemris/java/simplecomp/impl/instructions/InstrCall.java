package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

import java.util.List;

/**
 * Class implementation of the Instruction interface. Provides with the CALL operation.
 */
public class InstrCall implements Instruction {

    /**
     * private reference to the adress value
     */
    private int address;

    /**
     * Default class constructor
     *
     * @param arguments list of parser-read arguments given to the command
     */
    public InstrCall(List<InstructionArgument> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Expected 1 arguments!");
        }

        try {
            this.address = (Integer) arguments.get(0).getValue();
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
        Integer pc = computer.getRegisters().getProgramCounter();

        Integer location = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
        computer.getMemory().setLocation(location, pc);
        computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, location - 1);

        computer.getRegisters().setProgramCounter(address);

        return false;

    }
}

