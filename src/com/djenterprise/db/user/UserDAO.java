package com.djenterprise.db.user;

import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.connection.DBConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

//TODO Add avatar compatibility
//TODO Edit user
//TODO Get user
public class UserDAO {

    // Variable for logging
    static final private Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    static public void createUser (UserBO user){
        if( user.getUsername() == null || user.getPassword() == null ) {
            throw new RuntimeException("User " + user + " not set right. Username or password is missing.");
        }
        try{
            //Create query script
            String query =
                    "INSERT INTO User (username, password, alias) VALUES (?, ?, ?);";
            //Open DB connection
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Prepare safe statement
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getAlias());
            statement.execute();
            //Close DB connection
            DBConnection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }
}