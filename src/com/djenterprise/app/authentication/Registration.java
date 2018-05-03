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
     */
    static public void register( UserBO user ) {
        UserDAO.createUser(user);
    }

    /**
     * Check if alias is taken or not
     * @param alias alias to check
     * @throws UserAlreadyExistsException when alias is already taken
     * @return true if alias is available, otherwise throws exception
     */
    static public boolean checkAliasAvailability( String alias ) {
        try {
            UserDAO.getUserByAlias(alias);
            throw new UserAlreadyExistsException("User " + alias + " already exists.");
        } catch (EntityInstanceNotFoundException ex) {
            return true;
        }
    }

    /**
     * Check if username is taken or not
     * @param username username to check
     * @throws UserAlreadyExistsException when username is already taken
     * @return true if alias is available, otherwise throws exception
     */
    static public boolean checkUsernameAvailability( String username ) {
        try {
            UserDAO.getUser(username);
            throw new UserAlreadyExistsException("User " + username + " already exists.");
        } catch (EntityInstanceNotFoundException ex) {
            return true;
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