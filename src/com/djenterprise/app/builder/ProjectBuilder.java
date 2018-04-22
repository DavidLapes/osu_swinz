package com.djenterprise.app.builder;

import com.djenterprise.db.connection.DBConnection;
import org.apache.log4j.Logger;

import java.nio.file.Paths;

public class ProjectBuilder {

    // Variable for logging
    static final private Logger LOGGER = Logger.getLogger(ProjectBuilder.class.getName());

    static public void main( String[] args ) {
        DBConnection.initialize();
    }
}