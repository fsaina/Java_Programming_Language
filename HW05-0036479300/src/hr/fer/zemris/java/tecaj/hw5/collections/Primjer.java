package hr.fer.zemris.java.tecaj.hw5.collections;


/**
 * Primjer class implementation for testing the SimpleHashtable object
 */
public class Primjer {

    /**
     * Entry point of the application
     *
     * @param args command line arguments provided - non taken
     */
    public static void main(String[] args) { // create collection:
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
      
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); 

        for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
        }

        System.out.println("\n\nKartezijev produkt:\n");

        for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
            for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
                System.out.printf(
                        "(%s => %d) - (%s => %d)%n",
                        pair1.getKey(),
                        pair1.getValue(),
                        pair2.getKey(),
                        pair2.getValue()
                );
            }
        }


    }

}