package com.djenterprise.app.builder;

import com.djenterprise.db.connection.DBConnection;
import org.apache.log4j.Logger;

public class ProjectBuilder {

    // Variable for logging
    static final private Logger LOGGER = Logger.getLogger(ProjectBuilder.class.getName());

    /**
     * When running this method, be sure that no one is using your database and you are not doing anything with it either. This will completely rebuild the database, via JavaDoc of DBConnection.
     * @param args Empty.
     */
    static public void main( String[] args ) {
        LOGGER.info("Starting project");
        DBConnection.initialize();
        LOGGER.info("FERTIG!");
    }
}