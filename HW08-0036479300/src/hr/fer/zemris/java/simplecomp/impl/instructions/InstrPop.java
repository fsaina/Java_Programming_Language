package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

import java.util.List;

/**
 * Class implementation of the Instruction interface. Provides with the POP operation.
 */
public class InstrPop implements Instruction {

    /**
     * private reference to the index value of the register argument 1
     */
    private int indexRegistra1;

    /**
     * Default class constructor
     *
     * @param arguments list of parser-read arguments given to the command
     */
    public InstrPop(List<InstructionArgument> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Expected 1 arguments! "+arguments.size() + " given");
        }

        if (!arguments.get(0).isRegister() ||
                RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())) {
            throw new IllegalArgumentException(
                    "Type mismatch for argument " + 0 + "!"
            );
        }

        this.indexRegistra1 =
                RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());

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
        computer.getRegisters().setRegisterValue(indexRegistra1, value);
        computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, location + 1);


        return false;

    }
}

