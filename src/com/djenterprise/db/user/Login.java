package com.djenterprise.db.user;

import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.connection.DBConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(Logger.class.getName());

    /**
     * Tests the login with username and password of UserBO passed as parameter.
     * @param userBO User logging in.
     * @return true if username and password match, otherwise return false.
     */
    static public boolean testLogin( UserBO userBO ) {
        try {
            String username = userBO.getUsername();
            //Obtain encrypted password
            String password = userBO.encryptedPassword();
            String query = "SELECT * FROM User WHERE username = ? AND password = ?";
            //Prepare statement and connect to DB
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            //Does ResultSet contain element? - if so, login was successful
            boolean ret = resultSet.next();
            //Close ResultSet
            resultSet.close();
            //Close statement
            statement.close();
            //Close DB connection
            DBConnection.disconnect();
            //Sending to logger if login was successful or not
            if( ret ) {
                LOGGER.info("Login was successful.");
            } else {
                LOGGER.error("Wrong username or password!");
            }
            //Return the result of login attempt
            return ret;
        } catch (SQLException SQLEx) {
            throw new RuntimeException(SQLEx);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred during encryption.", ex);
        }
    }
}