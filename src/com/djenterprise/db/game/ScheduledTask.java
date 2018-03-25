package com.djenterprise.db.game;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("ALL")
public final class ScheduledTask extends TimerTask {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(ScheduledTask.class.getName());

    //TODO
    //Method to be run periodically
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("Time is: " + new Date());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    //TODO
    static public void removeGames() {

    }

    //TODO
    static public void init() {

        Properties properties = new Properties();
        FileInputStream inputStream;
        // Initialize Path to property file for DB CONNECTION
        Path path;
        // Is this Windows?
        if (System.getProperty("os.name").toLowerCase().contains("windows")){
            path = Paths.get("src\\com\\djenterprise\\config\\jdbc.properties");
            LOGGER.info("Reading file " + path.toString());
        }
        // It is not Windows
        else {
            path = Paths.get("src//com//djenterprise//config//jdbc.properties");
            LOGGER.info("Reading file " + path.toString());
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
        //JDBC_DRIVER = properties.getProperty("jdbc.driver");


        TimerTask scheduledTask = new ScheduledTask();
        Timer timer = new Timer();
        //timer.scheduleAtFixedRate(scheduledTask, );
    }
}