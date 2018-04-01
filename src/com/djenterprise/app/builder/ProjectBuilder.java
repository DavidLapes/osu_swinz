package com.djenterprise.app.builder;

import com.djenterprise.db.connection.DBConnection;
import org.apache.log4j.Logger;

import java.nio.file.Paths;

public class ProjectBuilder {

    // Variable for logging
    static final private Logger LOGGER = Logger.getLogger(ProjectBuilder.class.getName());

    static public void main( String[] args ) {
        //System.out.println(Paths.get("..\\osu_swinz\\src\\com\\djenterprise\\config\\jdbc.properties"));
        //LOGGER.info(ProjectBuilder.class.getResource("ProjectBuilder.class").toString());
        //LOGGER.info("Starting project");
        //DBConnection.initialize();
        //LOGGER.info("FERTIG!");
        //System.out.println("\\build\\web\\WEB-INF\\classes");
        DBConnection.initialize();
    }
}