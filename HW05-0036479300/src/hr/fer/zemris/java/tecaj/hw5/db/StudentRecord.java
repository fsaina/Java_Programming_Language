package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Class representation of a student record in the databse
 */
public class StudentRecord {

    /**
     * Jmbag field in the record
     */
    private String jmbag;

    /**
     * last name field in the record
     */
    private String lastName;

    /**
     * First name field in the record
     */
    private String firstName;

    /**
     * Final grade field in the record
     */
    private String finalGrade;

    /**
     * Default StudentRecord constructor object
     *
     * @param jmbag      jmbag provided String
     * @param lastName   last name proviede String
     * @param firstName  first name provided String
     * @param finalGrade final grade provided String
     */
    public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
        testIfArgumentIsNull(jmbag, lastName, firstName, finalGrade);

        this.jmbag = jmbag.trim();
        this.lastName = lastName.trim();
        this.firstName = firstName.trim();
        this.finalGrade = finalGrade.trim();

    }

    /*
     * Private method usde for testing the input arguments for null values
     */
    private void testIfArgumentIsNull(Object... objects) {
        for (Object o : objects)
            if (o == null)
                throw new IllegalArgumentException("StudentRecord argument provided element is null");
    }

    /**
     * Method override for testing the equals of StudentRecord objects
     *
     * @param obj object to be tested
     * @return true if equal, false othwerwise
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof StudentRecord)) {
            return false;
        }
        StudentRecord sr = (StudentRecord) obj;
        if (sr.jmbag.equals(this.jmbag)) return true;
        return false;
    }

    /**
     * Calculate the hash code of Student record
     *
     * @return int numebr representin the hashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
        return result;
    }

    /**
     * Method override of the string representatin of the studentRecord object
     *
     * @return
     */
    @Override
    public String toString() {
        return jmbag + " " + lastName + " " + firstName + " " + finalGrade;
    }

    /**
     * Getter for the last name element
     *
     * @return String of the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * getter fot the jmbag element
     *
     * @return String of the first name
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * getter for the first name elemnt
     *
     * @return String of the frist name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * getter for the final grade element
     *
     * @return return String represting the final grade
     */
    public String getFinalGrade() {
        return finalGrade;
    }

}
