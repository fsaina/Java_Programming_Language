package hr.fer.zemris.java.tecaj.hw6.demo5;

/**
 * Student record representation read from the student text file
 */
public class StudentRecord {

    /**
     * Variable representation of the JMBAG string
     */
    private String jmbag;

    /**
     * Variable representation of the surname string
     */
    private String prezime;

    /**
     * Variable representation of the name string
     */
    private String ime;

    /**
     * Variable representation of the midterms points double
     */
    private Double brojBodovaNaMeduispitu;

    /**
     * Variable representation of the finals ponits double
     */
    private Double brojBodovaNaZavrsnomIspitu;

    /**
     * Variable representation of the lab excersises double
     */
    private Double brojBodovaNaLaboratorijskimVjezbama;

    /**
     * Variable representation of the final grade int
     */
    private Integer ocjena;

    /**
     * Default class constructor
     *
     * @param jmbag                               jmbag number represntation
     * @param prezime                             surname representation
     * @param ime                                 name representation
     * @param brojBodovaNaMeduispitu              points on midterms representation
     * @param brojBodovaNaZavrsnomIspitu          points on finals representation
     * @param brojBodovaNaLaboratorijskimVjezbama points on lab excersises
     * @param ocjena                              final grade
     */
    public StudentRecord(String jmbag, String prezime, String ime, Double brojBodovaNaMeduispitu, Double brojBodovaNaZavrsnomIspitu, Double brojBodovaNaLaboratorijskimVjezbama, Integer ocjena) {
        this.jmbag = jmbag;
        this.prezime = prezime;
        this.ime = ime;
        this.brojBodovaNaMeduispitu = brojBodovaNaMeduispitu;
        this.brojBodovaNaZavrsnomIspitu = brojBodovaNaZavrsnomIspitu;
        this.brojBodovaNaLaboratorijskimVjezbama = brojBodovaNaLaboratorijskimVjezbama;
        this.ocjena = ocjena;
    }

    /**
     * Returns the sum of all student points from midterms, finals, lab exercises
     *
     * @return number of student points
     */
    public Double getStudentPoints() {
        return getBrojBodovaNaMeduispitu() +
                getBrojBodovaNaZavrsnomIspitu() +
                getBrojBodovaNaLaboratorijskimVjezbama();
    }

    /**
     * JMBAG value getter
     *
     * @return jmbag value
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Surname value getter
     *
     * @return surname value
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Name value getter
     *
     * @return name value
     */
    public String getIme() {
        return ime;
    }

    /**
     * Midterm points value getter
     *
     * @return midterm points
     */
    public Double getBrojBodovaNaMeduispitu() {
        return brojBodovaNaMeduispitu;
    }

    /**
     * Finals points value getter
     *
     * @return finsls points
     */
    public Double getBrojBodovaNaZavrsnomIspitu() {
        return brojBodovaNaZavrsnomIspitu;
    }

    /**
     * Lab excersise value getter
     *
     * @return lab value
     */
    public Double getBrojBodovaNaLaboratorijskimVjezbama() {
        return brojBodovaNaLaboratorijskimVjezbama;
    }

    /**
     * Final grade value getter
     *
     * @return final grade value
     */
    public Integer getOcjena() {
        return ocjena;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getJmbag() + "::" + getPrezime() + "::" + getIme();
    }
}
