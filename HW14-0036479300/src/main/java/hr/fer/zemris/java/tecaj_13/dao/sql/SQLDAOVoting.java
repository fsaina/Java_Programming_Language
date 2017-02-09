package hr.fer.zemris.java.tecaj_13.dao.sql;

import hr.fer.zemris.java.tecaj_13.DerbyUtils;
import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.PollOptionsEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by filipsaina on 14/06/16.
 */
public class SQLDAOVoting implements DAO {
    @Override
    public List<PollOptionsEntry> grabAllPollOptions() throws DAOException {
        List<PollOptionsEntry> poolOptions = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst = null;

        String query = "select id,optionTitle, optionLink, pollID, votesCount from PollOptions";

        try {
            pst = con.prepareStatement(query);
            try {
                ResultSet rs = pst.executeQuery();
                try {
                    while (rs != null && rs.next()) {
                        PollOptionsEntry options = new PollOptionsEntry();
                        options.setId(rs.getLong(1));
                        options.setTitle(rs.getString(2));
                        options.setLink(rs.getString(3));
                        options.setPoolId(rs.getLong(4));
                        options.setVotesCount(rs.getLong(5));
                        poolOptions.add(options);
                    }

                } finally {
                    try {
                        rs.close();
                    } catch (Exception ignorable) {
                    }
                }
            } catch (SQLException e) {

                if (!DerbyUtils.tableAlreadyExists(e)) {
                    createPoolOptionsTable(con);
                }


            } finally {

                try {
                    pst.close();
                } catch (Exception ignorable) {
                }
            }
        } catch (Exception ex) {
            throw new DAOException("Error while returning the pool options sql query", ex);
        }
        return poolOptions;
    }


    //If connection null ignore, if valid connection, perform query
    @Override
    public void createPoolOptionsTable(Connection connection) throws DAOException {
        Connection con;

        if (connection == null) {
            con = SQLConnectionProvider.getConnection();
        } else {
            con = connection;
        }

        PreparedStatement pst = null;

        String query = "CREATE TABLE PollOptions" +
                "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY" +
                ",optionTitle VARCHAR(100) NOT NULL," +
                "optionLink VARCHAR(150) NOT NULL," +
                "pollID BIGINT," +
                "votesCount BIGINT," +
                "FOREIGN KEY (pollID) REFERENCES Polls(id));";

        try {
            pst = con.prepareStatement(query);
            try {
                ResultSet rs = pst.executeQuery();

            } finally {

                try {
                    pst.close();
                } catch (Exception ignorable) {
                }
            }
        } catch (Exception ex) {
            throw new DAOException("Error while creating the pool options sql table", ex);
        }
    }
}
