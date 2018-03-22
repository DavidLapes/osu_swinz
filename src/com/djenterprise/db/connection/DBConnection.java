package com.djenterprise.db.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    // Database driver
    static private String JDBC_DRIVER;
    // Database URL string
    static private String JDBC_URL;
    // Database username login
    static private String JDBC_USERNAME;
    // Database password login
    static private String JDBC_PASSWORD;

    /**
     *
     */
    static public void initialize() {
        getDBProperties();
        testConnection();
        System.out.println("Success!");
    }

    /**
     *
     */
    static private void getDBProperties() {
        Properties properties = new Properties();
        FileInputStream inputStream;
        Path path = Paths.get("src\\com\\djenterprise\\config\\jdbc.properties");
        File file = new File(path.toUri());

        try {
            inputStream = new FileInputStream(file);
            properties.load(inputStream);
            inputStream.close();
        } catch ( FileNotFoundException FNFEx ) {
            throw new RuntimeException("No such file has been found '" + path + "'");
        } catch ( IOException IOEx ) {
            throw new RuntimeException("An error has occurred during reading the file '" + path + "'or closing FileInputStream");
        }

        String JDBC_TYPE = properties.getProperty("jdbc.type");
        String JDBC_IP_ADDRESS = properties.getProperty("jdbc.ip_address");
        String JDBC_PORT = properties.getProperty("jdbc.port");
        String JDBC_DB = properties.getProperty("jdbc.db");
        String JDBC_CONNECTION_CONFIGURATION = properties.getProperty("jdbc.connection_configuration");

        JDBC_DRIVER = properties.getProperty("jdbc.driver");
        JDBC_USERNAME = properties.getProperty("jdbc.username");
        JDBC_PASSWORD = properties.getProperty("jdbc.password");
        JDBC_URL =
                JDBC_TYPE + "://" + JDBC_IP_ADDRESS + ":" + JDBC_PORT + "/" + JDBC_DB + "?" + JDBC_CONNECTION_CONFIGURATION;
    }

    /**
     *
     */
    static private void testConnection() {
        if( JDBC_DRIVER == null ) {
            throw new RuntimeException("No JDBC driver found. Please check the patch or file 'jdbc.properties'");
        } else {
            try {
                Class.forName(JDBC_DRIVER);
            } catch ( ClassNotFoundException CNFEx ) {
                throw new RuntimeException("An error has occurred during initializing JDBC driver - ClassNotFoundException");
            }
        }

        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            connection.close();
        } catch ( SQLException SQLEx ) {
            throw new RuntimeException( SQLEx );
        }
    }

    // TODO
    static public void connect() {

    }

    // TODO
    static public void disconnect() {

    }
}