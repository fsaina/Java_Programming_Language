package hr.fer.zemris.web.glasanje;

/**
 * Model object abstraction modeling a single input from the DEFINITION_PATH in the RecordController object. A
 * sigle record represents a band object with all of its values.
 *
 * @author Filip Saina
 */
public class RecordModel {

    /**
     * Id record attribute
     */
    private String id;

    /**
     * Name record attribute
     */
    private String name;

    /**
     * Link record attribute
     */
    private String link;

    /**
     * Votes record attribute
     */
    private Integer votes;

    /**
     * Record class constructor.
     *
     * @param id   ID of the record
     * @param name Name of the band
     * @param link Link to the most famous song of the band
     */
    public RecordModel(String id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.votes = 0;
    }

    /**
     * Id getter
     *
     * @return String ID
     */
    public String getId() {
        return id;
    }

    /**
     * ID value setter
     *
     * @param id string id
     * @return RecordModel object
     */
    public RecordModel setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Name getter
     *
     * @return String Name
     */
    public String getName() {
        return name;
    }

    /**
     * Name value setter
     *
     * @param name string name
     * @return RecordModel object
     */
    public RecordModel setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Link getter
     *
     * @return String Link
     */
    public String getLink() {
        return link;
    }

    /**
     * Link value setter
     *
     * @param link string id
     * @return RecordModel object
     */
    public RecordModel setLink(String link) {
        this.link = link;
        return this;
    }

    /**
     * Votes getter
     *
     * @return Integer Votes
     */
    public Integer getVotes() {
        return votes;
    }

    /**
     * Votes value setter
     *
     * @param votes string id
     * @return RecordModel object
     */
    public RecordModel setVotes(Integer votes) {
        this.votes = votes;
        return this;
    }
}
