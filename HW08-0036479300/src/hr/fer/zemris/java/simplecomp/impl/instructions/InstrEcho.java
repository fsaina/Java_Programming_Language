package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Class implementation of the Instruction interface. Provides with the ECHO operation.
 */
public class InstrEcho implements Instruction {

    /**
     * Is indirect flag
     */
    private boolean isIndirect = false;

    /**
     * private reference to the index value of the register argument 1
     */
    private int indexRegistra1;

    /**
     * Offset of the value
     */
    private int offset;

    /**
     * Default class constructor
     *
     * @param arguments list of parser-read arguments given to the command
     */
    public InstrEcho(List<InstructionArgument> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Expected 1 arguments!");
        }

        if (RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())) {


            isIndirect = true;
            int argValue = (Integer) arguments.get(0).getValue();
            indexRegistra1 = RegisterUtil.getRegisterIndex(argValue);
            offset = RegisterUtil.getRegisterOffset(argValue);

        } else {

            this.indexRegistra1 =
                    RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());

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

        Object value1 = null;
        value1 = computer.getRegisters().getRegisterValue(indexRegistra1);

        if (isIndirect) {
            int location = (Integer) computer.getRegisters().getRegisterValue(indexRegistra1) + offset;
            value1 = computer.getMemory().getLocation(location);

        }


        System.out.print(value1);

        return false;
    }
}
