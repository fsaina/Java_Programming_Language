package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * Class showcasing the PrimesCollection class
 */
public class PrimesDemo1 {

    /**
     * Entry point of the application
     *
     * @param args command arguments provided - non taken
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(11); // 5: how many of them
        for(Integer prime : primesCollection) {
            System.out.println("Got prime: "+prime);
        }
    }
}
