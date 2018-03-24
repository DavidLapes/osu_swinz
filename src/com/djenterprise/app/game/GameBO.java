package com.djenterprise.app.game;

import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import com.djenterprise.db.game.GameDAO;

import java.sql.Timestamp;
import java.util.Random;

public class GameBO {

    private String gameId;
    private String creator;
    private Timestamp dateCreated;

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

    //TODO JavaDoc and comments
    public String generateId() {
        Random rand = new Random();
        boolean correctlySet = false;
        String gameId = null;
        while( ! correctlySet ) {
            try {
                int id = rand.nextInt(90000000) + 10000000;
                gameId = Integer.toString(id);
                GameDAO.getGame(gameId);
            } catch ( EntityInstanceNotFoundException EINFEx ) {
                correctlySet = true;
            }
        }
        return gameId;
    }
}