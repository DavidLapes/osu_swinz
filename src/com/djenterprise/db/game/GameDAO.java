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
//TODO Get Game
//TODO Assigning
public class GameDAO {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(GameDAO.class.getName());

    //TODO JavaDoc
    static public void createGame( GameBO gameBO) {
        try {
            //Create query script
            String query =
                    "";
            //Open DB connection
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Execute statement and save result into ResultSet
            ResultSet resultSet = statement.executeQuery();
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
                    "WHERE gamequestions.gameid_fk = " + gameBO.getGameId() + " " +
                    "AND gamequestions.questionid_fk = question.questionid;";
            //Open DB connection
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
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
            GameBO ret = null;
            //Create query script
            String query =
                    "SELECT creator, datecreated, gameid " +
                            "FROM game " +
                            "WHERE gameid = " + gameId + ";";
            //Open DB connection
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
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