package com.djenterprise.db.connection;

import com.djenterprise.app.builder.ProjectBuilder;
import com.ibatis.common.jdbc.ScriptRunner;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
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
    // Database CONNECTION
    private Connection CONNECTION = null;

    // Logger variable
    static final private Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    static {
        getDBProperties();
        registerDriver();
    }

    /**
     * Sets up DB CONNECTION and initializes variables for DB CONNECTION.
     */
    public void initialize() {
        LOGGER.warn("Your current OS is: " + System.getProperty("os.name"));
        // Initialize variables
        getDBProperties();
        // Registers the driver
        registerDriver();
        // Test if CONNECTION can be set up successfully
        testConnection();
        // Run MySQL DB-build script
        executeSQL();
    }

    /**
     * Initializes variables for DB CONNECTION.
     */
    static private void getDBProperties() {
        Properties properties = new Properties();
        FileInputStream inputStream;
        // Initialize Path to property file for DB CONNECTION
        Path path;
        // 08:24:07  INFO - file:/S:/SWINZ/osu_swinz/out/artifacts/osu_swinz_war_exploded/WEB-INF/classes/com/djenterprise/app/builder/ProjectBuilder.class
        // Is this Windows?
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                path = Paths.get(DBConnection.class.getResource("jdbc.properties").toURI());
                LOGGER.info("Reading file " + path.toString());
            }
            // It is not Windows
            else {
                path = Paths.get(DBConnection.class.getResource("jdbc.properties").toURI());
                LOGGER.info("Reading file " + path.toString());
            }
        } catch (URISyntaxException ex ) {
            throw new RuntimeException(ex);
        }

        // Get the File by specified Path
        File file = new File(path.toUri());

        try {
            LOGGER.info("Opening file stream");
            // Create file stream between property file and application
            inputStream = new FileInputStream(file);
            // Load property file
            LOGGER.info("Loading JDBC properties");
            properties.load(inputStream);
            // Close stream
            LOGGER.info("Closing file stream");
            inputStream.close();
        } catch ( FileNotFoundException FNFEx ) {
            // Specified file was not found
            LOGGER.error(FNFEx);
            throw new RuntimeException("No such file has been found '" + path + "'");
        } catch ( IOException IOEx ) {
            // Whops! Error while working with file or closing the stream
            LOGGER.error(IOEx);
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
     * Attempts to set up and, then, close CONNECTION to DB.
     */
    static private void testConnection() {
        try {
            // Set up the CONNECTION
            LOGGER.info("Logging to database " + JDBC_URL);
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            // Close the test CONNECTION
            LOGGER.info("Successfully logged to database!");
            LOGGER.info("Closing the database!");
            connection.close();
            LOGGER.info("Database closed.");
        } catch ( SQLException SQLEx ) {
            // Connection or disconnection has not been successful
            LOGGER.error(SQLEx);
            throw new RuntimeException( SQLEx );
        }
    }

    /**
     * Attempts to register the driver.
     */
    static private void registerDriver(){
        if( JDBC_DRIVER == null ) {
            // No JDBC driver has been found, throw an exception
            LOGGER.error("No JDBC driver has been found");
            throw new RuntimeException("No JDBC driver found. Please check the patch or file 'jdbc.properties'");
        } else {
            try {
                // Register MySQL JDBC driver
                LOGGER.info("Registering driver " + JDBC_DRIVER);
                Class.forName(JDBC_DRIVER);
            } catch ( ClassNotFoundException CNFEx ) {
                // Couldn't register the driver
                LOGGER.error(CNFEx);
                throw new RuntimeException("An error has occurred during registering JDBC driver");
            }
        }
    }

    /**
     * Attempts to establish connection to the database.
     */
    public void connect() {
        try {
            // Set up the CONNECTION
            LOGGER.info("Logging to database " + JDBC_URL);
            CONNECTION = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            LOGGER.info("Successfully logged to database!");
        } catch ( SQLException SQLEx ) {
            // Connection has not been successful
            LOGGER.error(SQLEx);
            throw new RuntimeException( SQLEx );
        }
    }

    /**
     * Attempts to close the connection to the database.
     */
    public void disconnect() {
        try{
            // Close the test CONNECTION
            LOGGER.info("Closing the database!");
            CONNECTION.close();
            LOGGER.info("Database closed.");
        } catch ( SQLException SQLEx ) {
            // Closing has not been successful
            LOGGER.error(SQLEx);
            throw new RuntimeException( SQLEx );
        } catch ( IllegalStateException ISEx){
            // Connection has been closed already
            LOGGER.error(ISEx);
        }
    }

    /**
     * Returns Connection object
     * @return this Connection
     */
    public Connection getCONNECTION() {
        return this.CONNECTION;
    }

    /**
     * Executes MySQL script to re-build DB
     */
    private void executeSQL() {
        // Save path to script
        String aSQLScriptFilePath = ProjectBuilder.class.getResource("dbbuilder.sql").toString();
        // Is this Windows?
        if( System.getProperty("os.name").toLowerCase().contains("windows") ) {
            // Build path correctly due to server directory where app is being run
            aSQLScriptFilePath = aSQLScriptFilePath.replaceAll("file:/", "");
            aSQLScriptFilePath = aSQLScriptFilePath.replaceAll("\\build\\web\\WEB-INF\\classes", "");
        }
        // This is not Windows
        else {
            // Build path correctly due to server directory where app is being run
            aSQLScriptFilePath = aSQLScriptFilePath.replaceAll("file:/", "");
            aSQLScriptFilePath = aSQLScriptFilePath.replaceAll("//build//web//WEB-INF//classes", "");
            aSQLScriptFilePath = "/" + aSQLScriptFilePath.replaceAll("/osu_swinz/out/production/osu_swinz","/osu_swinz/src");
        }
        // Connect to database
        connect();
        try {
            // Initialize script-runnable object
            ScriptRunner sr = new ScriptRunner(CONNECTION, false, false);
            // Initialize file reader
            Reader reader = new BufferedReader(new FileReader(aSQLScriptFilePath));
            // Run script
            sr.runScript(reader);
            // Close reader
            reader.close();
        } catch (IOException IOEx) {
            throw new RuntimeException("There was an error while reaching the file " + aSQLScriptFilePath);
        } catch (SQLException SQLEx) {
            throw new RuntimeException(SQLEx);
        }
        // Disconnect from database
        disconnect();
    }
}