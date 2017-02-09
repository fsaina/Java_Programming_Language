package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * Class implementation of the DAO interface. Every method declared there is implemented using the static
 * JPAEMProvider class that provides with entity manager instances. Implementations require just transactions,
 * but to commits. Those are handled in the JPAFilter.
 */
public class JPADAOImpl implements DAO {

    @Override
    public BlogEntry getBlogEntry(Long id) throws DAOException {
        BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
        return blogEntry;
    }

    @Override
    public BlogUser getBlogUserByNick(String nick) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();

        @SuppressWarnings("unchecked")
        List<BlogUser> users =
                (List<BlogUser>) em.createNamedQuery("BlogUser.getUserByNick")
                        .setParameter("be", nick)
                        .getResultList();

        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public void addBlogUser(BlogUser user) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();
        em.persist(user);
    }

    @Override
    public List<BlogUser> getAuthors() throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();

        List<BlogUser> authors =
                (List<BlogUser>) em.createNamedQuery("BlogUser.getAuthors")
                        .getResultList();

        return authors;
    }

    //entry must be a new object
    @Override
    public void addBlogEntryToUser(BlogEntry entry, BlogUser user) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();

        entry.setCreator(user);
        em.persist(entry);
    }

    @Override
    public List<BlogEntry> getEntriesMadeByUserWithNick(String nickname) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();

        @SuppressWarnings("unchecked")
        List<BlogEntry> authoredEntries =
                (List<BlogEntry>) em.createNamedQuery("BlogEntry.getEntriesForUserNick")
                        .setParameter("be", nickname)
                        .getResultList();


        return authoredEntries;
    }

    @Override
    public void addCommentToEntry(Long blogEntryID, String email, String comment) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();

        BlogEntry blogEntry = em.find(BlogEntry.class, blogEntryID);

        BlogComment blogComment = new BlogComment();
        blogComment.setUsersEMail(email);
        blogComment.setPostedOn(new Date());
        blogComment.setMessage(comment);
        blogComment.setBlogEntry(blogEntry);

        em.persist(blogComment);

        blogEntry.getComments().add(blogComment);
    }

}