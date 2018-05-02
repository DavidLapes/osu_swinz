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
            GameBO game =
                    GameDAO.getGame(
                            gameStateBO.getGameId()
                    );
            List<QuestionBO> questions = GameDAO.getQuestions(game);
            //Create query script
            String query =
                    "INSERT INTO GameState(gameid_fk, number_of_questions, current_round, player_one_points, player_two_points) VALUES( ?, ?, ?, ?, ?);";
            //Open DB connection and prepare a query statement
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setString(1, gameStateBO.getGameId());
            statement.setInt(2, questions.size());
            statement.setInt(3, 1);
            statement.setInt(4, 0);
            statement.setInt(5, 0);
            //Insert into DB and create the game
            statement.execute();
            //Close statement
            statement.close();
            //Disconnect from DB
            DBConnection.disconnect();
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
            // Declare return variable
            GameStateBO ret;
            //Create query script
            String query =
                    "SELECT creator, datecreated, gameid, player_one, player_two FROM Game WHERE gameid = ?;";
            //Open DB connection and prepare a query statement
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
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
            //Close result set
            resultSet.close();
            //Close statement
            statement.close();
            //Close DB connection
            DBConnection.disconnect();
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
            //Deleting assigned questions first
            String query =
                    "DELETE FROM GameState WHERE gameid_fk = ?;";
            //Prepare the statement
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setString(1, gameID);
            //Execute the statement
            statement.execute();
            //Close statement
            statement.close();
            //Close DB
            DBConnection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }
}