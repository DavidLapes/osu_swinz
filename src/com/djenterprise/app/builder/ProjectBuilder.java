package com.djenterprise.app.builder;

import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.user.UserDAO;
import org.apache.log4j.Logger;

import java.nio.file.Paths;

public class ProjectBuilder {

    // Variable for logging
    static final private Logger LOGGER = Logger.getLogger(ProjectBuilder.class.getName());

    static public void main( String[] args ) {
        DBConnection.initialize();
        UserBO user = new UserBO();
        user.setAlias("TestAlias");
        user.setUsername("TestUsername");
        user.setPassword("TestPassword");
        UserDAO.createUser(user);
    }
}