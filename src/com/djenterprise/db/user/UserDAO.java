package com.djenterprise.db.user;

import com.djenterprise.db.connection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

//TODO Add avatar compatibility
//TODO Edit user
//TODO Get user
public class UserDAO {

    public static void createUser (String userName, String userPassword, String userAlias){
        try{
            String query =
                    "INSERT INTO User (username, password, alias) VALUES (?, ?, ?);";
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, userPassword);
            statement.setString(3, userAlias);
            statement.execute();
            DBConnection.disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}