package com.djenterprise.db.game;

import com.djenterprise.app.game.AnswerBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerDAO {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(AnswerDAO.class.getName());

    /**
     * Adds inserted answer into the database.
     * @param answer answer to be added into the database.
     */
    @Deprecated
    public static void createAnswer(AnswerBO answer){
        try{
            //Create query
            String query =
                    "INSERT INTO Answer(text, questionid, truthfulness) VALUES (?,?,?)";
            //Prepares statement and opens connection to the database
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Inserts parameters into prepared statement
            statement.setString(1,answer.getText());
            statement.setInt(2,answer.getQuestionId());
            //Transforms boolean value into int value
            int truth = (answer.isCorrect()) ? 1 : 0;
            statement.setInt(3, truth);
            //Executes SQL command
            statement.execute();
            //Closes statement
            statement.close();
            //Closes connection to the database
            DBConnection.disconnect();

        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Returns answer with inserted ID if exists, otherwise throws EntityInstancceNotFoundException.
     * @param answerID ID of the requested answer
     * @return returns asnwer with inserted ID
     * @throws EntityInstanceNotFoundException when no Answer with input ID has been found
     */
    public static AnswerBO getAnswer(int answerID){
        try {
            //Creates query
            String query =
                    "SELECT * FROM ANSWER WHERE answerid = ?";
            //Prepares statement and opens connection to the database
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Inserts parameters into prepared statement
            statement.setInt(1, answerID);
            //Executes query and assigns it to a result set
            ResultSet rs = statement.executeQuery();
            //Checks for data
            if( ! rs.next() ) {
                throw new EntityInstanceNotFoundException("There is no answer with this id.");
            }
            //Inserts data into AnswerBO instance
            AnswerBO answer = new AnswerBO();
            answer.setAnswerId(rs.getInt("answerid"));
            answer.setText(rs.getString("text"));
            answer.setTruthfulness(rs.getInt("truthfulness"));
            answer.setQuestionId(rs.getInt("questionid"));
            //Closes result set
            rs.close();
            //Closes statement
            statement.close();
            //Closes connection to the database
            DBConnection.disconnect();
            //Returns the answer
            return answer;

        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }
}
