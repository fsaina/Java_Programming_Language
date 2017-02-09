package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * Second showcase class of the PrimesCollection class
 */
public class PrimesDemo2 {

    /**
     * Entry point of the application
     *
     * @param args command arguments provided - non taken
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(2);
        for (Integer prime : primesCollection) {
            for (Integer prime2 : primesCollection) {
                System.out.println("Got prime pair: " + prime + ", " + prime2);
            }
        }
    }

}
