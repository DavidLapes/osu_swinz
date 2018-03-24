package com.djenterprise.db.game;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.app.game.QuestionBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO Create Game
//TODO Delete game
public class GameDAO {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(GameDAO.class.getName());

    /**
     * Creates game and assigns questions to that game.
     * @param gameBO game to be added to DB.
     * @param questions List of questions which will be assigned to the game.
     */
    static public void createGame( GameBO gameBO, List<QuestionBO> questions ) {
        try {
            //Create query script
            String query =
                    "INSERT INTO Game(gameid, creator) VALUES( ?, ? );";
            //Open DB connection and prepare a query statement
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setString(1, gameBO.getGameId());
            statement.setString(2, gameBO.getCreator());
            //Insert into DB and create the game
            statement.execute();
            for( QuestionBO question : questions ) {
                query =
                        "INSERT INTO GameQuestions(gameid_fk, questionid_fk) VALUE ( ?, ? );";
                //Prepare query to statement
                statement = DBConnection.connect().prepareStatement(query);
                //Prepare statement
                statement.setString(1, gameBO.getGameId());
                statement.setInt(2, question.getQuestionId());
                //Execute query
                statement.execute();
            }
            //Close statement
            statement.close();
            //Disconnect from DB
            DBConnection.disconnect();
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
            // Declare return collection of questions
            List<QuestionBO> ret = new ArrayList<>();
            //Create query script
            String query =
                    "SELECT question.text " +
                    "FROM gamequestions, question " +
                    "WHERE gamequestions.gameid_fk = ? " +
                    "AND gamequestions.questionid_fk = question.questionid;";
            //Open DB connection and prepare a query statement
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setString(1, gameBO.getGameId());
            //Execute statement and save result into ResultSet
            ResultSet resultSet = statement.executeQuery();
            while( resultSet.next() ) {
                QuestionBO questionBO = new QuestionBO();
                questionBO.setText(resultSet.getString("text"));
                ret.add(questionBO);
            }
            //Close statement
            statement.close();
            //Close result set
            resultSet.close();
            //Close DB connection
            DBConnection.disconnect();
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
     */
    static public GameBO getGame( String gameId ) {
        try {
            // Declare return variable
            GameBO ret;
            //Create query script
            String query =
                    "SELECT creator, datecreated, gameid FROM Game WHERE gameid = ?;";
            //Open DB connection and prepare a query statement
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
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
}