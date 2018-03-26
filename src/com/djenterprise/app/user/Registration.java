package com.djenterprise.app.user;

import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import com.djenterprise.db.exceptions.UserAlreadyExistsException;
import com.djenterprise.db.user.UserDAO;
import org.apache.log4j.Logger;

public class Registration {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(Registration.class.getName());

    /**
     * Registers user.
     * @param user user to be registered.
     * @throws UserAlreadyExistsException when trying to register user with username which is already registered.
     */
    static public void register( UserBO user ) {
        try {
            UserDAO.getUser(user.getUsername());
            throw new UserAlreadyExistsException("User " + user.getUsername() + " already exists.");
        } catch ( EntityInstanceNotFoundException ex ) {
            UserDAO.createUser(user);
        }
    }
}