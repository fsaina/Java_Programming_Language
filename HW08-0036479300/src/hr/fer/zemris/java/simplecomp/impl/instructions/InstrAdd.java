package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Class implementation of the Instruction interface. Provides with the ADD operation.
 */
public class InstrAdd implements Instruction {

    /**
     * private reference to the index of a register
     */
    private int indexRegistra1;

    /**
     * private reference to the index of a register
     */
    private int indexRegistra2;

    /**
     * private reference to the index of a register
     */
    private int indexRegistra3;

    /**
     * Default class constructor
     *
     * @param arguments list of parser-read arguments given to the command
     */
    public InstrAdd(List<InstructionArgument> arguments) {
        if (arguments.size() != 3) {
            throw new IllegalArgumentException("Expected 3 arguments!");
        }

        for (int i = 0; i < 3; i++) {
            if (!arguments.get(i).isRegister() ||
                    RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
                throw new IllegalArgumentException(
                        "Type mismatch for argument " + i + "!"
                );
            }
        }

        this.indexRegistra1 =
                RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
        this.indexRegistra2 =
                RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
        this.indexRegistra3 =
                RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());

    }

    /**
     * Execution method for this command. Called on start of the execution.
     *
     * @param computer reference to the computer that executes the command.
     * @return true if execution finished normally, false otherwise
     */
    @Override
    public boolean execute(Computer computer) {

        Object value1 = computer.getRegisters().getRegisterValue(indexRegistra2);
        Object value2 = computer.getRegisters().getRegisterValue(indexRegistra3);

        computer.getRegisters().setRegisterValue(
                indexRegistra1,
                Integer.valueOf((Integer) value1 + (Integer) value2)
        );
        return false;

    }
}

