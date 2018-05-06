package com.djenterprise.db.game;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.app.game.GameStateBO;
import com.djenterprise.app.game.QuestionBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import com.djenterprise.db.user.UserDAO;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(GameDAO.class.getName());

    /**
     * Creates game and assigns questions to that game.
     * @param gameBO game to be added to DB.
     * @param questions List of questions which will be assigned to the game.
     * @throws EntityInstanceNotFoundException one of players' alias does not exist
     */
    static public void createGame( GameBO gameBO, List<QuestionBO> questions ) {
        //Check if players exist
        UserDAO.getUserByAlias(gameBO.getPlayerOne());
        UserDAO.getUserByAlias(gameBO.getPlayerTwo());

        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            //Create query script
            String query =
                    "INSERT INTO Game(gameid, creator, player_one, player_two) VALUES( ?, ?, ?, ? );";
            //Open DB connection and prepare a query statement
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameBO.getGameId());
            statement.setString(2, gameBO.getCreator());
            statement.setString(3, gameBO.getPlayerOne());
            statement.setString(4, gameBO.getPlayerTwo());
            //Insert into DB and create the game
            statement.execute();
            for( QuestionBO question : questions ) {
                query =
                        "INSERT INTO GameQuestions(gameid_fk, questionid_fk) VALUE ( ?, ? );";
                //Prepare query to statement
                statement = connection.getCONNECTION().prepareStatement(query);
                //Prepare statement
                statement.setString(1, gameBO.getGameId());
                statement.setInt(2, question.getQuestionId());
                //Execute query
                statement.execute();
            }
            //Close statement
            statement.close();
            //Disconnect from DB
            connection.disconnect();
            //Prepare state of game
            GameStateBO gameState = new GameStateBO();
            gameState.setPlayerOneAnswered(0);
            gameState.setPlayerTwoAnswered(0);
            gameState.setPlayerOnePoints(0);
            gameState.setPlayerTwoPoints(0);
            gameState.setGameId(gameBO.getGameId());
            gameState.setNumberOfQuestions(questions.size());
            gameState.setCurrentRound(1);
            //Create state of game
            GameStateDAO.createGameState(gameState);
        } catch ( SQLException SQLEx ) {
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Return questions according to GameBO instance
     * @param gameBO parameter of which the method returns assigned questions
     * @return collection containing questions assigned to the game
     */
    static public List<QuestionBO> getQuestions( GameBO gameBO ) {
        try{
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            // Declare return collection of questions
            List<QuestionBO> ret = new ArrayList<>();
            //Create query script
            String query =
                    "SELECT question.text, question.questionid " +
                    "FROM gamequestions, question " +
                    "WHERE gamequestions.gameid_fk = ? " +
                    "AND gamequestions.questionid_fk = question.questionid;";
            //Open DB connection and prepare a query statement
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameBO.getGameId());
            //Execute statement and save result into ResultSet
            ResultSet resultSet = statement.executeQuery();
            while( resultSet.next() ) {
                QuestionBO questionBO = new QuestionBO();
                questionBO.setQuestionId(resultSet.getInt("questionid"));
                questionBO.setText(resultSet.getString("text"));
                questionBO.setText(resultSet.getString("text"));
                ret.add(questionBO);
            }
            //Close result set
            resultSet.close();
            //Close statement
            statement.close();
            //Close DB connection
            connection.disconnect();
            //Return questions which game (gameBO) has available
            return ret;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Returns GameBO objects from database with specific ID
     * @param gameId 8-position ID of game which should be returned
     * @return built GameBO object according to gameID
     * @throws EntityInstanceNotFoundException when no Game with input ID has been found.
     */
    static public GameBO getGame( String gameId ) {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            // Declare return variable
            GameBO ret;
            //Create query script
            String query =
                    "SELECT creator, datecreated, gameid, player_one, player_two FROM Game WHERE gameid = ?;";
            //Open DB connection and prepare a query statement
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            //Execute statement and save result into ResultSet
            ResultSet resultSet = statement.executeQuery();
            if( ! resultSet.next() ) {
                throw new EntityInstanceNotFoundException("No game with ID " + gameId + " was found.");
            }
            ret = new GameBO();
            ret.setGameId(resultSet.getString("gameid"));
            ret.setCreator(resultSet.getString("creator"));
            ret.setDateCreated(resultSet.getTimestamp("datecreated"));
            ret.setPlayerOne(resultSet.getString("player_one"));
            ret.setPlayerTwo(resultSet.getString("player_two"));
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
     * Returns games older than input time in seconds as a collection.
     * @param timeInSeconds age of game
     * @return collection of games older than timeInSeconds
     */
    static public List<GameBO> getGamesOlderThan( int timeInSeconds ) {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            //Initialize list
            List<GameBO> games = new ArrayList<>();
            //Select games older than timeInSeconds
            String query = "SELECT * FROM Game WHERE datecreated < (NOW() - INTERVAL ? SECOND );";
            //Connect to DB and prepare statement
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setInt(1, timeInSeconds);
            //Execute the query
            ResultSet resultSet = statement.executeQuery();
            //Add found games to list
            while( resultSet.next() ) {
                GameBO gameBO = new GameBO();
                gameBO.setGameId(resultSet.getString("gameid"));
                gameBO.setCreator(resultSet.getString("creator"));
                gameBO.setDateCreated(resultSet.getTimestamp("datecreated"));
                games.add( gameBO );
            }
            //Close ResultSet
            resultSet.close();
            //Close statement
            statement.close();
            //Close DB
            connection.disconnect();
            //Return games found
            return games;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Delete game by ID.
     * @param gameID ID of the game to be deleted
     */
    static public void deleteGame( String gameID ) {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            //Delete state of the game
            GameStateDAO.deleteGameState(gameID);
            //Deleting assigned questions first
            String query =
                    "DELETE FROM GameQuestions WHERE gameid_fk = ?;";
            //Prepare the statement
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameID);
            //Execute the statement
            statement.execute();
            //Now prepare the query for deleting game itself from DB
            query =
                    "DELETE FROM Game WHERE gameid = ?;";
            //Initialize statement again
            statement = connection.getCONNECTION().prepareStatement(query);
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
     * Checks if the player is Player one in DB
     * @param alias Alias of player
     * @param gameId ID of game
     * @return True if Player is set as Player one
     */
    static public boolean isPlayerOne(String alias, String gameId){
        try{
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            //Variable for alias from DB
            String dbAlias;
            //Create query script
            String query =
                    "SELECT player_one FROM Game WHERE gameid = ?;";
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setString(1, gameId);
            //Execute statement and save result into ResultSet
            ResultSet resultSet = statement.executeQuery();
            if( ! resultSet.next() ) {
                throw new EntityInstanceNotFoundException("No game with ID " + gameId + " was found.");
            }
            dbAlias = resultSet.getString("player_one");
            //Close result set
            resultSet.close();
            //Close statement
            statement.close();
            //Close DB connection
            connection.disconnect();
            return alias.equals(dbAlias);
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }
}