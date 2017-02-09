package hr.fer.zemris.java.tecaj_13;

import java.sql.SQLException;

/**
 * Util class for the derby database specific method tests and implementation
 */
public class DerbyUtil {

    /**
     * Check if the table entry already exists in the database. The test is performed on the exception message
     * provided. For more information visit: http://stackoverflow.com/questions/5866154/how-to-create-table-if-it-doesnt-exist-using-derby-db
     *
     * @param e exception message
     * @return true if exists, false otherwise
     */
    public static boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if (e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }
}
