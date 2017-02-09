package hr.fer.zemris.java.tecaj_13.model;

import javax.persistence.*;
import java.util.Date;


/**
 * Class model of the database BLOG_COMMENTS relation
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

    /**
     * id filed database value
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * blogEntry filed database value
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private BlogEntry blogEntry;

    /**
     * usersEMail filed database value
     */
    @Column(nullable = false, length = 60)
    private String usersEMail;

    /**
     * message filed database value
     */
    @Column(nullable = false, length = 2 * 1024)
    private String message;

    /**
     * postedOn filed database value
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date postedOn;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets blog entry.
     *
     * @return the blog entry
     */
    public BlogEntry getBlogEntry() {
        return blogEntry;
    }

    /**
     * Sets blog entry.
     *
     * @param blogEntry the blog entry
     */
    public void setBlogEntry(BlogEntry blogEntry) {
        this.blogEntry = blogEntry;
    }

    /**
     * Gets users e mail.
     *
     * @return the users e mail
     */
    public String getUsersEMail() {
        return usersEMail;
    }

    /**
     * Sets users e mail.
     *
     * @param usersEMail the users e mail
     */
    public void setUsersEMail(String usersEMail) {
        this.usersEMail = usersEMail;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets posted on.
     *
     * @return the posted on
     */
    public Date getPostedOn() {
        return postedOn;
    }

    /**
     * Sets posted on.
     *
     * @param postedOn the posted on
     */
    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlogComment other = (BlogComment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}