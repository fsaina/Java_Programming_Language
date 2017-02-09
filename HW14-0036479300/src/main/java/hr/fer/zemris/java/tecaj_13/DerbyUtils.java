package hr.fer.zemris.java.tecaj_13;

import java.sql.SQLException;

/**
 * Created by filipsaina on 14/06/16.
 */
public class DerbyUtils {


    //http://stackoverflow.com/questions/5866154/how-to-create-table-if-it-doesnt-exist-using-derby-db
    public static boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if(e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }


}
