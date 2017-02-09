package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.util.List;


public interface DAO {

    /**
     * Returns the entry with the given id. If the requested entry does not exist, null is returned.
     *
     * @param id key for which to search blog entries
     * @return BlogEntry object containg the entry
     *
     * @throws DAOException thrown in case of error while executing the request
     */
    public BlogEntry getBlogEntry(Long id) throws DAOException;

    /**
     * Returns the BlogUser object abstraction by nickname. If the requested entry does not exist null is
     * returned.
     *
     * @param nick key for which to search blog entries
     * @return BlogUser object representing the nickname
     *
     * @throws DAOException thrown in case of error while executing the request
     */
    public BlogUser getBlogUserByNick(String nick) throws DAOException;

    /**
     * Add blog user entry to the list of existing users
     *
     * @param user Blog entry to be added
     * @throws DAOException thrown in case of error while executing the request
     */
    public void addBlogUser(BlogUser user) throws DAOException;

    /**
     * Method returns a list of all authors currently in the database. A author is a user database entry whose
     * creator filed is not null.
     *
     * @return List of BlogUser objects
     *
     * @throws DAOException thrown in case of error while executing the request
     */
    public List<BlogUser> getAuthors() throws DAOException;

    /**
     * Method add a blog entry to a ceramic user
     *
     * @param entry BlogEntry object to be added
     * @param user  Bloguser object to associate the entry with
     * @throws DAOException thrown in case of error while executing the request
     */
    public void addBlogEntryToUser(BlogEntry entry, BlogUser user) throws DAOException;

    /**
     * Returns all entries made be a user with the given nickname. If no entries are found a empty list is
     * returned
     *
     * @param nickname String object nickname to be found
     * @return List of BlogEntry object that meed the criteria
     *
     * @throws DAOException
     */
    public List<BlogEntry> getEntriesMadeByUserWithNick(String nickname) throws DAOException;

    /**
     * Method adds a comment input to a entry element.
     *
     * @param blogEntryID Id number of the blog entry to which to add the comment
     * @param email       email string of the user posting
     * @param comment     String comment to be stored in the comment
     * @throws DAOException thrown in case of error while executing the request
     */
    public void addCommentToEntry(Long blogEntryID, String email, String comment) throws DAOException;

}