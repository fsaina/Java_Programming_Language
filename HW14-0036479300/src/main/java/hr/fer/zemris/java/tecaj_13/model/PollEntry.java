package hr.fer.zemris.java.tecaj_13.model;

/**
 * Model object of the sql Poll table entry
 */
public class PollEntry {

    /**
     * Id of the entry
     */
    private long id;

    /**
     * Title of the entry
     */
    private String title;

    /**
     * Message of the entry
     */
    private String message;

    /**
     * Initial id number
     */
    public static long initialIdNumber = -1;

    /**
     * Default class constructor
     *
     * @param title   title of the entry
     * @param message message of the entry
     */
    public PollEntry(String title, String message) {
        this.title = title;
        this.message = message;
    }

    /**
     * Class constructor with no arugments
     */
    public PollEntry() {
    }

    /**
     * Id getter method
     *
     * @return long, id number
     */
    public long getId() {
        return id;
    }

    /**
     * Id setter method
     *
     * @param id number long to set the id
     * @return PollEntry class
     */
    public PollEntry setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * Title getter method
     *
     * @return String, Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the Title varaible
     *
     * @param title String title to be set
     * @return PollEntry
     */
    public PollEntry setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Message getter method
     *
     * @return String, Message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the Message varaible
     *
     * @param message String title to be set
     * @return PollEntry
     */
    public PollEntry setMessage(String message) {
        this.message = message;
        return this;
    }
}
