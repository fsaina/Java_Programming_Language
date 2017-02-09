package hr.fer.zemris.java.tecaj_13.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Blog entry.
 */
@Entity
@Table(name = "blog_entries")
@Cacheable
@NamedQueries({
        @NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when"),
        @NamedQuery(name = "BlogEntry.getEntriesForUserNick", query = "select b from BlogEntry as b where b.creator.nick=:be")
})
public class BlogEntry {

    /**
     * id filed database value
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * creator filed database value
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private BlogUser creator;

    /**
     * comments filed database value
     */
    @OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("postedOn")
    private List<BlogComment> comments = new ArrayList<>();

    /**
     * createdAt filed database value
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    /**
     * lastModifiedAt filed database value
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date lastModifiedAt;

    /**
     * title filed database value
     */
    @Column(nullable = false, length = 200)
    private String title;

    /**
     * text filed database value
     */
    @Column(nullable = false, length = 4 * 1024)
    private String text;

    /**
     * Gets creator.
     *
     * @return the creator
     */
    public BlogUser getCreator() {
        return creator;
    }

    /**
     * Sets creator.
     *
     * @param creator the creator
     * @return the creator
     */
    public BlogEntry setCreator(BlogUser creator) {
        this.creator = creator;
        return this;
    }

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
     * Gets comments.
     *
     * @return the comments
     */
    public List<BlogComment> getComments() {
        return comments;
    }

    /**
     * Sets comments.
     *
     * @param comments the comments
     */
    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets last modified at.
     *
     * @return the last modified at
     */
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Sets last modified at.
     *
     * @param lastModifiedAt the last modified at
     */
    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
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
        BlogEntry other = (BlogEntry) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}