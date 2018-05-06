package com.djenterprise.db.game;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.app.game.GameStateBO;
import com.djenterprise.app.game.QuestionBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class GameStateDAO {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(GameStateDAO.class.getName());

    /**
     * Create new state of game with GameStateBO.
     * @param gameStateBO state of game at the beginning
     */
    static public void createGameState(GameStateBO gameStateBO) {
        //Check if players exist
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            GameBO game =
                    GameDAO.getGame(
                            gameStateBO.getGameId()
                    );
            List<QuestionBO> questions = GameDAO.getQuestions(game);
            //Create query script
            String query =
                    "INSERT INTO GameState(gameid_fk, number_of_questions, current_round, player_one_points, player_two_points, player_one_answered, player_two_answered, player_one_connected, player_two_connected) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            //Open DB connection and prepare a query statement
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameStateBO.getGameId());
            statement.setInt(2, questions.size());
            statement.setInt(3, 1);
            statement.setInt(4, 0);
            statement.setInt(5, 0);
            statement.setBoolean(6, false);
            statement.setBoolean(7, false);
            statement.setBoolean(8, false);
            statement.setBoolean(9, false);
            //Insert into DB and create the game
            statement.execute();
            //Close statement
            statement.close();
            //Disconnect from DB
            connection.disconnect();
        } catch ( SQLException SQLEx ) {
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Gets state of game by GameID
     * @param gameId id of game
     * @return state of game with id gameId
     */
    static public GameStateBO getGameState( String gameId ) {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            // Declare return variable
            GameStateBO ret;
            //Create query script
            String query =
                    "SELECT gameid_fk, number_of_questions, current_round, player_one_points, player_two_points, player_one_connected, player_two_connected FROM GameState WHERE gameid_fk = ?;";
            //Open DB connection and prepare a query statement
            PreparedStatement statement =connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            //Execute statement and save result into ResultSet
            ResultSet resultSet = statement.executeQuery();
            if( ! resultSet.next() ) {
                throw new EntityInstanceNotFoundException("No game with ID " + gameId + " was found.");
            }
            ret = new GameStateBO();
            ret.setGameId(resultSet.getString("gameid_fk"));
            ret.setNumberOfQuestions(resultSet.getInt("number_of_questions"));
            ret.setCurrentRound(resultSet.getInt("current_round"));
            ret.setPlayerOnePoints(resultSet.getInt("player_one_points"));
            ret.setPlayerTwoPoints(resultSet.getInt("player_two_points"));
            ret.setPlayerOneConnected(resultSet.getInt("player_one_connected"));
            ret.setPlayerTwoConnected(resultSet.getInt("player_two_connected"));
            //Close result set
            resultSet.close();
            //Close statement
            statement.close();
            //Close DB connection
            connection.disconnect();
            //Return GameBO instance
            return ret;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Deletes saved state of game
     * @param gameID id of game
     */
    static public void deleteGameState( String gameID ) {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            //Deleting assigned questions first
            String query =
                    "DELETE FROM GameState WHERE gameid_fk = ?;";
            //Prepare the statement
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameID);
            //Execute the statement
            statement.execute();
            //Close statement
            statement.close();
            //Close DB
            connection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Increment current round by one
     * @param gameId id of game
     */
    static public void nextRound( String gameId ) {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            String query =
                    "UPDATE GameState SET current_round = current_round + 1, player_one_connected = 0," +
                            " player_two_connected = 0 WHERE gameid_fk = ?;";
            //Prepare the statement
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            //Execute the statement
            statement.execute();
            //Close statement
            statement.close();
            GameStateDAO.nowToDB(gameId);
            //Close DB
            connection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Increases points of Player One
     * @param gameId id of game
     * @param playerOnePointsIncrease points to increment
     */
    static public void playerOnePointsIncrease( String gameId, int playerOnePointsIncrease ) {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            String query =
                    "UPDATE GameState SET player_one_points = player_one_points + ? WHERE gameid_fk = ?;";
            //Prepare the statement
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setInt(1, playerOnePointsIncrease);
            statement.setString(2, gameId);
            //Execute the statement
            statement.execute();
            //Close statement
            statement.close();
            //Close DB
            connection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Increases points of Player Two
     * @param gameId id of game
     * @param playerTwoPointsIncrease points to increment
     */
    static public void playerTwoPointsIncrease( String gameId, int playerTwoPointsIncrease ) {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            String query =
                    "UPDATE GameState SET player_two_points = player_two_points + ? WHERE gameid_fk = ?;";
            //Prepare the statement
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setInt(1, playerTwoPointsIncrease);
            statement.setString(2, gameId);
            //Execute the statement
            statement.execute();
            //Close statement
            statement.close();
            //Close DB
            connection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Connection status of both players
     * @param gameId id of game
     * @return true if both players are connected
     * @throws EntityInstanceNotFoundException
     */
    static public boolean isBothConnected(String gameId){
        try{
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            // Declare return variable
            boolean ret;
            //Create query script
            String query =
                    "SELECT player_one_connected, player_two_connected FROM GameState WHERE gameid_fk = ?;";
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            //Execute statement and save result into ResultSet
            ResultSet resultSet = statement.executeQuery();
            if( ! resultSet.next() ) {
                throw new EntityInstanceNotFoundException("No game with ID " + gameId + " was found.");
            }
            ret = (0 != resultSet.getInt("player_one_connected"));
            ret = (ret && ( 0 != resultSet.getInt("player_two_connected")));
            //Close result set
            resultSet.close();
            //Close statement
            statement.close();
            //Close DB connection
            connection.disconnect();
            //Return GameBO instance
            return ret;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    public static void setConnected(boolean conn,  String gameId, String alias){
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            String query =
                    "SELECT player_one FROM Game WHERE gameid = ?;";

            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            //Execute statement and save result into ResultSet
            ResultSet resultSet = statement.executeQuery();
            if( ! resultSet.next() ) {
                throw new EntityInstanceNotFoundException("No game with ID " + gameId + " was found.");
            }
            String testAlias = resultSet.getString("player_one");

            //Close result set
            resultSet.close();

            if (alias.equals(testAlias)){
                query =
                        "UPDATE GameState SET player_one_connected = ?";
            } else {
                query =
                        "UPDATE GameState SET player_two_connected = ?";
            }

            statement = connection.getCONNECTION().prepareStatement(query);
            statement.setBoolean(1, conn);
            statement.execute();

            //Close statement
            statement.close();
            //Close DB connection
            connection.disconnect();

        } catch (SQLException SQLEx){
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    static public LocalTime getStartingTime(String gameId){
        try{
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            // Declare return variable
            LocalTime ret;
            //Create query script
            String query =
                    "SELECT question_start FROM GameState WHERE gameid_fk = ?;";
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            //Execute statement and save result into ResultSet
            ResultSet resultSet = statement.executeQuery();
            if( ! resultSet.next() ) {
                throw new EntityInstanceNotFoundException("No game with ID " + gameId + " was found.");
            }
            ret = resultSet.getTime("question_start").toLocalTime();
            //Close result set
            resultSet.close();
            //Close statement
            statement.close();
            //Close DB connection
            connection.disconnect();
            //Return GameBO instance
            return ret;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    public static void nowToDB(String gameId){
        try{
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            //Create query script
            String query =
                    "UPDATE GameState SET question_start = CURTIME() WHERE gameid_fk = ?;";
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            statement.execute();
            //Close statement
            statement.close();
            //Close DB connection
            connection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    public static int getCurrentRound(String gameId){
        try{
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            int ret;
            //Create query script
            String query =
                    "SELECT current_round FROM GameState WHERE gameid_fk = ?;";
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            if( ! resultSet.next() ) {
                throw new EntityInstanceNotFoundException("No game with ID " + gameId + " was found.");
            }
            ret = resultSet.getInt("current_round");
            resultSet.close();
            //Close statement
            statement.close();
            //Close DB connection
            connection.disconnect();
            return ret;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    static public boolean isConnected(String alias, String gameId){
        try{
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            // Declare return variable
            boolean ret;
            //Create query script
            String query;
            boolean playerOne = GameDAO.isPlayerOne(alias,gameId);
            if(playerOne){
                query = "SELECT player_one_connected FROM GameState WHERE gameid_fk = ?;";
            } else {
                query = "SELECT player_two_connected FROM GameState WHERE gameid_fk = ?;";
            }
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            //Execute statement and save result into ResultSet
            ResultSet resultSet = statement.executeQuery();
            if( ! resultSet.next() ) {
                throw new EntityInstanceNotFoundException("No game with ID " + gameId + " was found.");
            }
            if (playerOne){
                ret = resultSet.getBoolean("player_one_connected");
            } else {
                ret = resultSet.getBoolean("player_two_connected");
            }
            //Close result set
            resultSet.close();
            //Close statement
            statement.close();
            //Close DB connection
            connection.disconnect();
            //Return GameBO instance
            return ret;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    public static void gameStart(String gameId){
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            String query =
                    "UPDATE GameState SET gamestarted = 1  WHERE gameid_fk = ?;";
            PreparedStatement statement;
            statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            statement.execute();
            //Close statement
            statement.close();
            //Close DB connection
            connection.disconnect();

        } catch (SQLException SQLEx){
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    public static boolean isGameStarted(String gameId){
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            boolean ret;
            String query =
                    "SELECT gamestarted FROM GameState WHERE gameid_fk = ?;";
            PreparedStatement statement;
            statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            if( ! resultSet.next() ) {
                throw new EntityInstanceNotFoundException("No game with ID " + gameId + " was found.");
            }
            ret = resultSet.getBoolean("gamestarted");

            resultSet.close();
            //Close statement
            statement.close();
            //Close DB connection
            connection.disconnect();
            return ret;

        } catch (SQLException SQLEx){
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

}