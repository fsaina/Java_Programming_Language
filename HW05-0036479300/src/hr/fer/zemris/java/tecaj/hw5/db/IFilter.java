package hr.fer.zemris.java.tecaj.hw5.db;


/**
 * Filter interface contract that obliges the implementing classes to implement a accept method
 */
public interface IFilter {
    /**
     * Method tests the given studentRecord object if it is accepted for a certain criteria
     *
     * @param record StudentRecord to be tested
     * @return true if is accepted, false otherwise
     */
    boolean accepts(StudentRecord record);
}
