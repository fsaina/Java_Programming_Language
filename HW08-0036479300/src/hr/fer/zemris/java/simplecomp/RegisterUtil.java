package hr.fer.zemris.java.simplecomp;

/**
 * Util class used for bit manipulation with indirect values. All manipulation is made with 32 bit
 * values(int).
 */
public class RegisterUtil {

    /**
     * Mask used for getting only the last 8 bits
     */
    private static final int MASK_7_TO_0 = 0x000000FF;

    /**
     * Mask used for getting only the 24-th bit
     */
    private static final int MASK_24_BIT = 0x01000000;

    /**
     * Mask used for retrieving bits from 8-th to 22-nd position
     */
    private static final int MASK_22_TO_8 = 0x00FFFF00;

    /**
     * Method returns the register index which is contained in the least significant 8 bits of a 32 bit value
     *
     * @param registerDescriptor 32 bit value
     * @return 32 bit representation of the extracted 8 bits
     */
    public static int getRegisterIndex(int registerDescriptor) {

        return (int) registerDescriptor & MASK_7_TO_0;

    }

    /**
     * Method tests the given 32 bit value if it is indirect typed. That information is contained in the 24-th
     * bit
     *
     * @param registerDescriptor 32 bit value to be tested
     * @return true if indirect(1), false otherwise
     */
    public static boolean isIndirect(int registerDescriptor) {

        int c = registerDescriptor & MASK_24_BIT;

        if (c == 0)
            return false;

        return true;

    }

    /**
     * Method returns the register descriptor of a 32 bit value. Returns -1 if the register value is not
     * indirect.
     *
     * @param registerDescriptor 32 bit value to be tested
     * @return 32 bit result containing the bits from 8th to 22nd
     */
    public static int getRegisterOffset(int registerDescriptor) {
        if (!isIndirect(registerDescriptor)) {
            return -1;
        }

        int number = (MASK_22_TO_8 & registerDescriptor) >> 8;
        int negative = number & 0x00008000;
        if (negative == 0) {
            return number;
        } else {
            return convertFromDoubleComplement(number);
        }
    }

    /**
     * Private method used for conversion from double complement to decimal value
     *
     * @param doubleComplNum int in double complement
     * @return int value to be retrieved
     */
    private static int convertFromDoubleComplement(int doubleComplNum) {
        String stringBin = Integer.toBinaryString(doubleComplNum);

        int absoluteValue = ((~doubleComplNum) & 0x0000FFFF) + 1;

        return stringBin.charAt(0) == '1' ? -absoluteValue : absoluteValue;
    }


}
