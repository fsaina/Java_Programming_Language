package hr.fer.zemris.java.tecaj_13.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Blog user.
 */
@Entity
@Table(name = "blog_users")
@NamedQueries({
        @NamedQuery(name = "BlogUser.getUserByNick", query = "select b from BlogUser as b where b.nick=:be"),
        @NamedQuery(name = "BlogUser.getAuthors", query = "select b from BlogUser as b where b.entries is not empty")
})
public class BlogUser {

    /**
     * id filed database value
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * entries filed database value
     */
    @OneToMany(mappedBy = "creator")
    private List<BlogEntry> entries = new ArrayList<>();

    /**
     * nick filed database value
     */
    @Column(nullable = false, length = 200, unique = true)
    private String nick;

    /**
     * firstName filed database value
     */
    @Column(nullable = true, length = 200)
    private String firstName;

    /**
     * lastName filed database value
     */
    @Column(nullable = true, length = 200)
    private String lastName;

    /**
     * email filed database value
     */
    @Column(nullable = true, length = 200)
    private String email;

    /**
     * passwordHash filed database value
     */
    @Column(nullable = false, length = 256)
    private String passwordHash;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     * @return the first name
     */
    public BlogUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Sets id.
     *
     * @param id the id
     * @return the id
     */
    public BlogUser setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Gets entries.
     *
     * @return the entries
     */
    public List<BlogEntry> getEntries() {
        return entries;
    }

    /**
     * Sets entries.
     *
     * @param entries the entries
     * @return the entries
     */
    public BlogUser setEntries(List<BlogEntry> entries) {
        this.entries = entries;
        return this;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     * @return the last name
     */
    public BlogUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Gets nick.
     *
     * @return the nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * Sets nick.
     *
     * @param nick the nick
     * @return the nick
     */
    public BlogUser setNick(String nick) {
        this.nick = nick;
        return this;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     * @return the email
     */
    public BlogUser setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Gets password hash.
     *
     * @return the password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets password hash.
     *
     * @param passwordHash the password hash
     * @return the password hash
     */
    public BlogUser setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }
}
