package com.djenterprise.app.authentication;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.game.GameDAO;
import com.djenterprise.db.user.UserDAO;

public class GameEntry {

    /**
     *  Decides if user belongs to the game or not to prevent more than 2 players.
     * @param username user entering the game
     * @param gameID game entered by user
     * @return true if entry is correct
     */
    static public boolean canEntry( String username, String gameID ) {
        UserBO user = UserDAO.getUser(username);
        GameBO game = GameDAO.getGame(gameID);
        return user.getAlias().equals(game.getPlayerOne()) || user.getAlias().equals(game.getPlayerTwo());
    }
}