package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Provider class implementation of the JPAEM provider object. It handles
 */
public class JPAEMProvider {

    /**
     * Local thread pool per request
     */
    private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

    /**
     * Getter method for entity managers
     *
     * @return EntityManager object containing data
     */
    public static EntityManager getEntityManager() {
        LocalData ldata = locals.get();
        if (ldata == null) {
            ldata = new LocalData();
            ldata.em = JPAEMFProvider.getEmf().createEntityManager();
            ldata.em.getTransaction().begin();
            locals.set(ldata);
        }
        return ldata.em;
    }

    /**
     * Method used for closing exceptions that occur
     *
     * @throws DAOException Thrown in case a error
     */
    public static void close() throws DAOException {
        LocalData ldata = locals.get();
        if (ldata == null) {
            return;
        }
        DAOException dex = null;
        try {
            ldata.em.getTransaction().commit();
        } catch (Exception ex) {
            dex = new DAOException("Unable to commit transaction.", ex);
        }
        try {
            ldata.em.close();
        } catch (Exception ex) {
            if (dex != null) {
                dex = new DAOException("Unable to close entity manager.", ex);
            }
        }
        locals.remove();
        if (dex != null) throw dex;
    }

    private static class LocalData {
        EntityManager em;
    }

}