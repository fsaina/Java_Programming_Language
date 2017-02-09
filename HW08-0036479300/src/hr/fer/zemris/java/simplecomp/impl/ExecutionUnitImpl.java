package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * Execution unit implementing the ExecutionUnit interface. Represents the working logic of a computer command
 * by command execution.
 */
public class ExecutionUnitImpl implements ExecutionUnit {

    /**
     * Method called for start of execution
     *
     * @param computer Computer class on which to execute
     * @return true if execution finished ok
     */
    @Override
    public boolean go(Computer computer) {

        computer.getRegisters().setProgramCounter(0);

        while (true) {

            int programCounter = computer.getRegisters().getProgramCounter();

            Instruction instruction = (Instruction) computer.getMemory().getLocation(programCounter);

            computer.getRegisters().incrementProgramCounter();

            boolean status = true;

            try {
                status = instruction.execute(computer);
            }catch (Exception e){
                System.err.println("Error in program execution!");
                e.printStackTrace();
                return false;
            }

            if (status)
                break;

        }

        return true;
    }
}
