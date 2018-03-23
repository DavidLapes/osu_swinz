package com.djenterprise.db.user;

import com.djenterprise.db.connection.DBConnection;
import net.bytebuddy.dynamic.scaffold.MethodRegistry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

//TODO Add avatar compatibility
public class UserDAO {
    public static void createUser (String userName, String userPassword, String userAlias){
        try{
            String query =
                    "INSERT INTO User (username, password, alias) VALUES (?, ?, ?);";
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setString(1,userName);
            statement.setString(2,userPassword);
            statement.setString(3,userAlias);
            statement.execute();
            DBConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



/*
    public static void createPerson( String personName, String personCity ) {
        try {
            Statement statement = JDBConnection.getConnection().createStatement();
            String query =
                    "INSERT INTO Person(name, city) "
                            + "VALUES('"
                            + personName + "','"
                            + personCity + "');"
                    ;
            statement.executeUpdate(query);
        } catch( SQLException sqlEx ) {
            LOGGER.error("Connection to DB has failed!");
        }
    }*/