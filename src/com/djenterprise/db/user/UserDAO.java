package com.djenterprise.db.user;

import com.djenterprise.app.user.AESenc;
import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//TODO Edit user - display name and avatar and password
//TODO Avatar DB compatibility
public class UserDAO {

    // Variable for logging
    static final private Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    /**
     * Adds inserted user into the databaase.
     * @param user user to be added to the databse.
     */
    static public void createUser (UserBO user){
        try{
            //Creates query script
            String query =
                    "INSERT INTO User (username, password, avatar, alias) VALUES (?, ?, ?, ?);";
            //Opens DB connection
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            /*
            //Locates and opens the file
            File file = new File(File Path String Variable"");
            FileInputStream fis = new FileInputStream(file);
            //Prepares safe statement
            */
            statement.setString(1, user.getUsername());
            statement.setString(2, user.encryptedPassword());
            statement.setBlob(3,user.getAvatar());
            //Transforms file into binary stream
            //statement.setBinaryStream(3, fis, (int) file.length());
            statement.setString(4, user.getAlias());
            //Executes SQL command
            statement.execute();
            //Closes the statement
            statement.close();
            //Close DB connection
            DBConnection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        } /*catch (FileNotFoundException FNFEx){
            LOGGER.error(FNFEx);
            throw new RuntimeException(FNFEx);
        } */catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }   

    /**
     * Returns the user with inserted username, or throws EntityInstanceNotFoundException if user not found.
     * @param username username of the requested user.
     * @return returns requested user.
     * @throws EntityInstanceNotFoundException when no User with input username has been found.
     */
    static public UserBO getUser(String username){
        try{
            //Query creation
            String query =
                    "SELECT * FROM USER WHERE username = ?";
            //Retrieving connection to the database
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Inserting values into prepared statement
            statement.setString(1, username);
            //Executing query
            ResultSet rs = statement.executeQuery();
            UserBO user = new UserBO();
            //Checks for data
            if( ! rs.next() ) {
                throw new EntityInstanceNotFoundException("There is no user with this username.");
            }
            //Setting userBO values
            user.setUsername(rs.getString("username"));
            user.setAlias(rs.getString("alias"));
            user.setAvatar(rs.getBlob("avatar"));
            //Closes the result set
            rs.close();
            //Closes the statement
            statement.close();
            //Closes the database connection
            DBConnection.disconnect();
            //Returns the user
            return user;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    public static void editUserAvatar(UserBO user){
        try{
            //Query creation
            String query =
                    "UPDATE USER SET avatar = ? WHERE username = ?";
            //Retrieving connection to the database
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Locates and opens the file

            File file = new File(/*File Path String Variable*/"");
            FileInputStream fis = new FileInputStream(file);

            //Inserting values into prepared statement
            //Transforms file into binary stream
            statement.setBinaryStream(1, fis, (int) file.length());
            statement.setString(2, user.getUsername());
            //Executes SQL command
            statement.execute();
            //Closes the statement
            statement.close();
            //Close DB connection
            DBConnection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        } catch (FileNotFoundException FNFEx){
            LOGGER.error(FNFEx);
            throw new RuntimeException(FNFEx);
        }
    }
    public static void editUserAlias(UserBO user){
        try {
            //Query creation
            String query =
                    "UPDATE USER SET alias = ? WHERE username = ?";
            //Retrieving connection to the database
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Inserting values into prepared statement
            statement.setString(1, user.getAlias());
            statement.setString(2, user.getUsername());
            //Executes SQL command
            statement.execute();
            //Closes the statement
            statement.close();
            //Close DB connection
            DBConnection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    public static void editUserPassword(UserBO user){
        try {
            //Query creation
            String query =
                    "UPDATE USER SET password = ? WHERE username = ?";
            //Retrieving connection to the database
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Inserting values into prepared statement
            statement.setString(1, user.encryptedPassword());
            statement.setString(2, user.getUsername());
            //Executes SQL command
            statement.execute();
            //Closes the statement
            statement.close();
            //Close DB connection
            DBConnection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}