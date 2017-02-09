package hr.fer.zemris.java.tecaj_13.dao;


import hr.fer.zemris.java.tecaj_13.model.PollOptionsEntry;

import java.sql.Connection;
import java.util.List;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 *
 * @author marcupic
 */
public interface DAO {



    public List<PollOptionsEntry> grabAllPollOptions() throws DAOException;

    public void createPoolOptionsTable(Connection connection) throws DAOException;

//    /**
//     * Dohvaća sve postojeće unose u bazi, ali puni samo dva podatka: id i title.
//     *
//     * @return listu unosa
//     *
//     * @throws DAOException u slučaju pogreške
//     */
//    public List<Unos> dohvatiOsnovniPopisUnosa() throws DAOException;
//
//    /**
//     * Dohvaća Unos za zadani id. Ako unos ne postoji, vraća <code>null</code>.
//     *
//     * @param id
//     * @return
//     *
//     * @throws DAOException
//     */
//    public Unos dohvatiUnos(long id) throws DAOException;

}