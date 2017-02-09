package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Register class implementation
 */
public class RegistersImpl implements Registers {

    /**
     * Data store of the Object used
     */
    private Object[] register;

    /**
     * Program counter of the computer
     */
    private int programCounter;

    /**
     * Flag indication if a certain condition is met
     */
    private boolean flag;

    /**
     * Default class constructor
     *
     * @param regsLen initial number of registers
     */
    public RegistersImpl(int regsLen) {
        if (regsLen < 1) {
            throw new IllegalArgumentException("Size of the register array cannot be less than 1");
        }
        register = new Object[regsLen];
        programCounter = 0;
        flag = false;
    }

    /**
     * Getter for the certain registers used
     *
     * @param index position of the register inside the array
     * @return Object at a given position
     */
    @Override
    public Object getRegisterValue(int index) {
        if (index < 0 || index >= register.length)
            throw new IndexOutOfBoundsException("Acessing undefinded register location. Value: " + index);

        return register[index];
    }

    /**
     * Setter of register values
     *
     * @param index position to be changed
     * @param value value to change at a given position
     */
    @Override
    public void setRegisterValue(int index, Object value) {
        if (index < 0 || index >= register.length)
            throw new IndexOutOfBoundsException("Acessing undefinded register location. Value: " + index);

        register[index] = value;
    }

    /**
     * Program counter variable getter
     *
     * @return program counter value
     */
    @Override
    public int getProgramCounter() {
        return programCounter;
    }

    /**
     * Program counter setter
     *
     * @param value sets the program counter to a given value
     */
    @Override
    public void setProgramCounter(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Program counter cannot obtaion negative values");
        }
        programCounter = value;
    }

    /**
     * Increment program counter for 1
     */
    @Override
    public void incrementProgramCounter() {
        programCounter++;
    }

    /**
     * Getter for the flag value
     *
     * @return boolean of the flag value
     */
    @Override
    public boolean getFlag() {
        return flag;
    }

    /**
     * Setter fot the flag value
     *
     * @param value sets the value
     */
    @Override
    public void setFlag(boolean value) {
        flag = value;
    }
}
