package com.djenterprise.app.builder;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.game.GameDAO;
import org.apache.log4j.Logger;

public class ProjectBuilder {

    // Variable for logging
    static final private Logger LOGGER = Logger.getLogger(ProjectBuilder.class.getName());

    static public void main( String[] args ) {
        LOGGER.info("Starting project");
        DBConnection.initialize();
        LOGGER.info("FERTIG!");

        //GameBO gameBO = new GameBO();
        //GameDAO.getQuestions(gameBO);
    }
}