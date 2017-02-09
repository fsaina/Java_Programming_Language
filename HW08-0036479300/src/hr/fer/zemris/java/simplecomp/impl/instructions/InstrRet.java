package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

import java.util.List;

/**
 * Class implementation of the Instruction interface. Provides with the RET operation.
 */
public class InstrRet implements Instruction {

    /**
     * Default class constructor
     *
     * @param arguments list of parser-read arguments given to the command
     */
    public InstrRet(List<InstructionArgument> arguments) {
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

        Integer location = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
        Integer value = (Integer) computer.getMemory().getLocation(location + 1);
        computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, location + 1);

        computer.getRegisters().setProgramCounter(value);
        return false;

    }
}

