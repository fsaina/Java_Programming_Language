package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * DAOProvider class implementation
 */
public class DAOProvider {

    /**
     * Private instance of the DAO interface implementation
     */
    private static DAO dao = new JPADAOImpl();

    /**
     * Application available public DAO getter
     *
     * @return DAO object implementation singletion
     */
    public static DAO getDAO() {
        return dao;
    }

}