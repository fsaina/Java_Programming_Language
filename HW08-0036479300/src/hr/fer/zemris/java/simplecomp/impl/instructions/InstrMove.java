package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Class implementation of the Instruction interface. Provides with the MOVE operation.
 */
public class InstrMove implements Instruction {

    /**
     * Flag if the first argument is indirect
     */
    private boolean arg1IsIndirect = false;

    /**
     * Flag if the first argument is indirect
     */
    private boolean arg2IsIndirect = false;

    /**
     * private reference to the index value of the register argument 1
     */
    private int indexRegistra1;

    /**
     * private reference to the index value of the register argument 1
     */
    private int indexRegistra2;

    /**
     * Offset of the first argument
     */
    private int offset1 = 0;

    /**
     * Offset of the second argument
     */
    private int offset2 = 0;

    private Integer val2 = null;


    /**
     * Default class constructor
     *
     * @param arguments list of parser-read arguments given to the command
     */
    public InstrMove(List<InstructionArgument> arguments) {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Expected 2 arguments!");
        }

        if (!arguments.get(0).isRegister()) {
            throw new IllegalArgumentException(
                    "Type mismatch for argument " + 0 + "!"
            );
        }


        if (RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())) {

            arg1IsIndirect = true;
            int arg1Value = (Integer) arguments.get(0).getValue();
            indexRegistra1 = RegisterUtil.getRegisterIndex(arg1Value);
            offset1 = RegisterUtil.getRegisterOffset(arg1Value);

        } else {
            this.indexRegistra1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
        }


        if (arguments.get(1).isNumber()) {
            this.val2 = (Integer) arguments.get(1).getValue();

        } else if (arguments.get(1).isRegister()) {

            if (RegisterUtil.isIndirect((Integer) arguments.get(1).getValue())) {

                arg2IsIndirect = true;
                int arg2Value = (Integer) arguments.get(1).getValue();

                indexRegistra2 = RegisterUtil.getRegisterIndex(arg2Value);
                offset2 = RegisterUtil.getRegisterOffset(arg2Value);


            } else {
                this.indexRegistra2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
            }

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

        Object value;

        if (arg2IsIndirect) {
            int location = (Integer) computer.getRegisters().getRegisterValue(indexRegistra2) + offset2;
            value = computer.getMemory().getLocation(location);
        } else if (val2 != null) {
            value = val2;
        } else {
            value = computer.getRegisters().getRegisterValue(indexRegistra2);
        }

        if (arg1IsIndirect) {
            int location = (Integer) computer.getRegisters().getRegisterValue(indexRegistra1) + offset1;
            computer.getMemory().setLocation(location, value);
        } else {
            computer.getRegisters().setRegisterValue(indexRegistra1, value);
        }


        return false;
    }
}
