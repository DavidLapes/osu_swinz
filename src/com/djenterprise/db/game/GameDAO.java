package com.djenterprise.db.game;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.app.game.QuestionBO;
import com.djenterprise.db.connection.DBConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO Create Game
//TODO Get Game
//TODO Get Question
//TODO Assigning
public class GameDAO {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(GameDAO.class.getName());

    //TODO JavaDoc
    static public void createGame() {

    }

    //TODO JavaDoc
    static public List<QuestionBO> getQuestions( GameBO gameBO ) {
        List<QuestionBO> ret = new ArrayList<>();
        try{
            //Create query script
            String query =
                    "SELECT questionid_fk FROM gamequestions WHERE gameid_fk = '" + gameBO.getGameId() + "';";
            //Open DB connection
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Execute statement and save result into ResultSet
            ResultSet resultSet = statement.executeQuery();
            while( resultSet.next() ) {
                QuestionBO questionBO = new QuestionBO();
                questionBO.setText(resultSet.getString("text"));
                ret.add(questionBO);
            }
            //Close DB connection
            DBConnection.disconnect();
            //Return questions which game (gameBO) has available
            return ret;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    //TODO JavaDoc
    static public GameBO getGame( GameBO gameBO ) {
        GameBO ret = null;
        //TODO
        return ret;
    }
}