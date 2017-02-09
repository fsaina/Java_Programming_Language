package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Class implementation of the Instruction interface. Provides with the ADD operation.
 */
public class InstrTestEquals implements Instruction {

    /**
     * private reference to the index value of the register argument 1
     */
    private int indexRegistra1;

    /**
     * private reference to the index value of the register argument 2
     */
    private int indexRegistra2;

    /**
     * Default class constructor
     *
     * @param arguments list of parser-read arguments given to the command
     */
    public InstrTestEquals(List<InstructionArgument> arguments) {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Expected 3 arguments!");
        }

        for (int i = 0; i < 2; i++) {
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

    }

    /**
     * Execution method for this command. Called on start of the execution.
     *
     * @param computer reference to the computer that executes the command.
     * @return true if execution finished normally, false otherwise
     */
    @Override
    public boolean execute(Computer computer) {
        Object value1 = computer.getRegisters().getRegisterValue(indexRegistra1);
        Object value2 = computer.getRegisters().getRegisterValue(indexRegistra2);

        if (value1.equals(value2)) {
            computer.getRegisters().setFlag(true);
        } else {
            computer.getRegisters().setFlag(false);
        }

        return false;

    }
}

