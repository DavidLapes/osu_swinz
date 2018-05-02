package com.djenterprise.app.game;

import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import com.djenterprise.db.game.GameDAO;

import java.sql.Timestamp;
import java.util.Random;

public class GameBO {

    private String gameId;
    private String creator;
    private Timestamp dateCreated;
    private String playerOne;
    private String playerTwo;

    public String getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(String playerOne) {
        this.playerOne = playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(String playerTwo) {
        this.playerTwo = playerTwo;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    /**
     * Generates random 8-number ID.
     * @return generated ID.
     */
    public String generateId() {
        //Initialize Random generator
        Random rand = new Random();
        //ID has not been set yet - false
        boolean correctlySet = false;
        //No gameID yet - null
        String gameId = null;
        //Found yet?
        while( ! correctlySet ) {
            try {
                //Generate ID between 10000000 and 99999999
                int id = rand.nextInt(90000000) + 10000000;
                //Convert number to string
                gameId = Integer.toString(id);
                //Some game with this ID has been found, repeat
                GameDAO.getGame(gameId);
            //No such game with this ID has been found, exit generating
            } catch ( EntityInstanceNotFoundException EINFEx ) {
                //ID set correctly
                correctlySet = true;
            }
        }
        //Return ID
        return gameId;
    }
}