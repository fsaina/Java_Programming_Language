package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Class implementation of the Instruction interface. Provides with the LOAD operation.
 */
public class InstrLoad implements Instruction {

    /**
     * private reference to the index value of the register argument 1
     */
    private int indexRegistra1;

    /**
     * Adress memory of the arg1
     */
    private int memAdresa;


    /**
     * Default class constructor
     *
     * @param arguments list of parser-read arguments given to the command
     */
    public InstrLoad(List<InstructionArgument> arguments) {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Expected 2 arguments!");
        }

        if (!arguments.get(0).isRegister() ||
                RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())) {
            throw new IllegalArgumentException(
                    "Type mismatch for argument " + 0 + "!"
            );
        }

        this.indexRegistra1 =
                RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());

        try {
            this.memAdresa = (Integer) arguments.get(1).getValue();
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Second argument in load instr. not integer! ");
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

        computer.getRegisters().setRegisterValue(indexRegistra1,
                computer.getMemory().getLocation(memAdresa));

        return false;
    }
}
