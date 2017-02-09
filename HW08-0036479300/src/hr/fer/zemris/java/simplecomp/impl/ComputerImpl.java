package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementation of the computer class
 */
public class ComputerImpl implements Computer {

    /**
     * Private reference to the register stored
     */
    private Registers registers;

    /**
     * Private reference to the memory
     */
    private Memory memory;

    /**
     * Default constructor
     *
     * @param memorySize initial size of the memory
     * @param regSize    initial size of the register
     */
    public ComputerImpl(int memorySize, int regSize) {
        registers = new RegistersImpl(regSize);
        memory = new MemoryImpl(memorySize);
    }

    /**
     * Getter for registers used
     *
     * @return Registers in the computer
     */
    @Override
    public Registers getRegisters() {
        return registers;
    }

    /**
     * Getter for the memory used
     *
     * @return Memory object representation
     */
    @Override
    public Memory getMemory() {
        return memory;
    }
}
