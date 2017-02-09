package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class provider giving access to the EntityManagerFactory singleton instance. That instance is later closed
 * in JPAFilter class.
 */
public class JPAEMFProvider {

    /**
     * Singleton instance of the provided class
     */
    public static EntityManagerFactory emf;

    /**
     * Getter method for the EntityManagerFactory
     *
     * @return EntityManagerFactory object instance
     */
    public static EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * Singleton setter method
     *
     * @param emf EntityManagerFactory object instance
     */
    public static void setEmf(EntityManagerFactory emf) {
        JPAEMFProvider.emf = emf;
    }
}