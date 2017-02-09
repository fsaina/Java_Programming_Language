package hr.fer.zemris.java.tecaj.hw5.collections;

/**
 * SimpleHashtableDemo class for testing the SimpleHashtable object
 */
public class SimpleHashtableDemo {

    /**
     * Entry point of the applicaiton
     *
     * @param args console arguemnts proviede(non required)
     */
    public static void main(String[] args) {
        // create collection:
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        // fill data:
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana

        // query collection:
        Integer kristinaGrade = examMarks.get("Kristina");
        System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5

        // What is collection's size? Must be four!
        System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4

        System.out.println(examMarks.toString());
    }
}