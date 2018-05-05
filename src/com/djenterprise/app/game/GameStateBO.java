package com.djenterprise.app.game;

public class GameStateBO {

    private String gameId;
    private int numberOfQuestions;
    private int currentRound;
    private int playerOnePoints;
    private int playerTwoPoints;
    private boolean playerOneAnswered;
    private boolean playerTwoAnswered;
    private boolean playerOneConnected;
    private boolean playerTwoConnected;

    /**
     * Proceeds to next round and adds winning points.
     * @param playerOnePoints points to add to second player
     * @param playerTwoPoints points to add to second player
     */
    public void nextRound( int playerOnePoints, int playerTwoPoints ) {
        currentRound ++;
        playerOneIncreaseBy(playerOnePoints);
        playerTwoIncreaseBy(playerTwoPoints);
    }

    /**
     * Increases player's points.
     * @param playerOnePoints winning player
     */
    private void playerOneIncreaseBy( int playerOnePoints ) {
        this.playerOnePoints += playerOnePoints;
    }

    /**
     * Increases player's point.
     * @param playerTwoPoints winning player
     */
    private void playerTwoIncreaseBy( int playerTwoPoints ) {
        this.playerTwoPoints = playerTwoPoints;
    }

    public boolean isPlayerOneAnswered() {
        return playerOneAnswered;
    }

    public void setPlayerOneAnswered(int playerOneAnswered) {
        if( playerOneAnswered <= 0 ) {
            this.playerOneAnswered = false;
        } else {
            this.playerOneAnswered = true;
        }
    }

    public boolean isPlayerTwoAnswered() {
        return playerTwoAnswered;
    }

    public void setPlayerTwoAnswered(int playerTwoAnswered) {
        if( playerTwoAnswered <= 0 ) {
            this.playerTwoAnswered = false;
        } else {
            this.playerTwoAnswered = true;
        }
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setGameId(GameBO gameBO) {
        this.gameId = gameBO.getGameId();
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getPlayerOnePoints() {
        return playerOnePoints;
    }

    public void setPlayerOnePoints(int playerOnePoints) {
        this.playerOnePoints = playerOnePoints;
    }

    public int getPlayerTwoPoints() {
        return playerTwoPoints;
    }

    public void setPlayerTwoPoints(int playerTwoPoints) {
        this.playerTwoPoints = playerTwoPoints;
    }

    public void setPlayerOneAnswered(boolean playerOneAnswered) {
        this.playerOneAnswered = playerOneAnswered;
    }

    public void setPlayerTwoAnswered(boolean playerTwoAnswered) {
        this.playerTwoAnswered = playerTwoAnswered;
    }

    public boolean isPlayerOneConnected() {
        return playerOneConnected;
    }

    public void setPlayerOneConnected(int playerOneConnected) {
        if( playerOneConnected <= 0 ) {
            this.playerOneConnected = false;
        } else {
            this.playerOneConnected = true;
        }
    }

    public void setPlayerOneConnected(boolean playerOneConnected) {
        this.playerOneConnected = playerOneConnected;
    }

    public boolean isPlayerTwoConnected() {
        return playerTwoConnected;
    }

    public void setPlayerTwoConnected(int playerTwoConnected) {
        if( playerTwoConnected <= 0 ) {
            this.playerTwoConnected = false;
        } else {
            this.playerTwoConnected = true;
        }
    }

    public void setPlayerTwoConnected(boolean playerTwoConnected) {
        this.playerTwoConnected = playerTwoConnected;
    }

}