package com.djenterprise.app.game;

import com.djenterprise.db.connection.DBConnection;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public final class ScheduledTask extends TimerTask {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(ScheduledTask.class.getName());

    /**
     * This runs whenever scheduler is run. It is called in init().
     */
    @Override
    public void run() {
        //WARNING announcement when DB is being updated
        LOGGER.warn("Games older than " + getResources().getProperty("time") + " are being removed from DB!");
        //Remove games
        removeGames();
    }

    //TODO
    static private void removeGames() {
        try {

            Properties properties = getResources();
            int interval = Integer.parseInt(properties.getProperty("time"));
            interval /= 1000;

            String query = "";
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Method to be run at server container startup. Initializes periodical game deleting.
     */
    static public void init() {
        TimerTask scheduledTask = new ScheduledTask();
        Timer timer = new Timer();
        Properties properties = getResources();
        int delay = Integer.parseInt(properties.getProperty("delay"));
        int period = Integer.parseInt(properties.getProperty("period"));
        timer.scheduleAtFixedRate(scheduledTask, delay, period);
    }

    /**
     * Gets property file with configuration attributes for scheduler.
     * @return Property file
     */
    static public Properties getResources() {
        Properties properties = new Properties();
        FileInputStream inputStream;
        // Initialize Path to property file
        Path path;
        // Is this Windows?
        if (System.getProperty("os.name").toLowerCase().contains("windows")){
            path = Paths.get("src\\com\\djenterprise\\config\\db_schedule.properties");
            LOGGER.info("Reading file " + path.toString());
        }
        // It is not Windows
        else {
            path = Paths.get("src//com//djenterprise//config//db_schedule.properties");
            LOGGER.info("Reading file " + path.toString());
        }

        // Get the File by specified Path
        File file = new File(path.toUri());

        try {
            LOGGER.info("Opening file stream");
            // Create file stream between property file and application
            inputStream = new FileInputStream(file);
            // Load property file
            LOGGER.info("Loading scheduler properties");
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
        return properties;
    }
}