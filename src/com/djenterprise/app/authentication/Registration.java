package com.djenterprise.app.authentication;

import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import com.djenterprise.db.exceptions.UserAlreadyExistsException;
import com.djenterprise.db.user.UserDAO;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.djenterprise.app.authentication.MatchingPatterns.ALIAS_REGEX;
import static com.djenterprise.app.authentication.MatchingPatterns.PASSWORD_REGEX;
import static com.djenterprise.app.authentication.MatchingPatterns.USERNAME_REGEX;

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

    /**
     * Checks if username is correctly filled.
     * @param username username to be checked
     * @return true if username is correct, otherwise false
     */
    static public boolean checkUsername( String username ) {
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.find();
    }

    /**
     * Checks if alias is correctly filled.
     * @param alias alias to be checked
     * @return true if alias is correct, otherwise false
     */
    static public boolean checkAlias( String alias ) {
        Pattern pattern = Pattern.compile(ALIAS_REGEX);
        Matcher matcher = pattern.matcher(alias);
        return matcher.find();
    }

    /**
     * Checks if password is correctly filled.
     * @param password password to be checked
     * @return true if alias is correct, otherwise false
     */
    static public boolean checkPassword( String password ) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}