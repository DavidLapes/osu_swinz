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
     * Sets up DB connection and initializes variables for DB connection.
     */
    static public void initialize() {
        // Initialize variables
        getDBProperties();
        // Test if connection can be set up successfully
        testConnection();
        // Everything went fine
        System.out.println("Success!");
    }

    /**
     * Initializes variables for DB connection.
     */
    static private void getDBProperties() {
        Properties properties = new Properties();
        FileInputStream inputStream;
        // Initialize Path to property file for DB connection
        Path path = Paths.get("src\\com\\djenterprise\\config\\jdbc.properties");
        // Get the File by specified Path
        File file = new File(path.toUri());

        try {
            // Create file stream between property file and application
            inputStream = new FileInputStream(file);
            // Load property file
            properties.load(inputStream);
            // Close stream
            inputStream.close();
        } catch ( FileNotFoundException FNFEx ) {
            // Specified file was not found
            throw new RuntimeException("No such file has been found '" + path + "'");
        } catch ( IOException IOEx ) {
            // Whops! Error while working with file or closing the stream
            throw new RuntimeException("An error has occurred during reading the file '" + path + "'or closing FileInputStream");
        }

        // Get parts for setting up JDBC_URL
        String JDBC_TYPE = properties.getProperty("jdbc.type");
        String JDBC_IP_ADDRESS = properties.getProperty("jdbc.ip_address");
        String JDBC_PORT = properties.getProperty("jdbc.port");
        String JDBC_DB = properties.getProperty("jdbc.db");
        String JDBC_CONNECTION_CONFIGURATION = properties.getProperty("jdbc.connection_configuration");

        // Get JDBC properties
        JDBC_DRIVER = properties.getProperty("jdbc.driver");
        JDBC_USERNAME = properties.getProperty("jdbc.username");
        JDBC_PASSWORD = properties.getProperty("jdbc.password");
        JDBC_URL =
                JDBC_TYPE + "://" + JDBC_IP_ADDRESS + ":" + JDBC_PORT + "/" + JDBC_DB + "?" + JDBC_CONNECTION_CONFIGURATION;
    }

    /**
     * Attempts to set up and, then, close connection to DB.
     */
    static private void testConnection() {
        if( JDBC_DRIVER == null ) {
            // No JDBC driver has been found, throw an exception
            throw new RuntimeException("No JDBC driver found. Please check the patch or file 'jdbc.properties'");
        } else {
            try {
                // Register MySQL JDBC driver
                Class.forName(JDBC_DRIVER);
            } catch ( ClassNotFoundException CNFEx ) {
                // Couldn't register the driver
                throw new RuntimeException("An error has occurred during registering JDBC driver");
            }
        }

        try {
            // Set up the connection
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            // Close the test connection
            connection.close();
        } catch ( SQLException SQLEx ) {
            // Connection or disconnection has not been successful
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