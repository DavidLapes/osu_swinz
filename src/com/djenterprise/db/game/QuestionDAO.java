package com.djenterprise.db.game;

import com.djenterprise.app.game.QuestionBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO JavaDoC
//TODO Comments
public class QuestionDAO {

    static final private Logger LOGGER = Logger.getLogger(QuestionDAO.class.getName());

    /**
     * Inserts a question into the database
     * @param questionBO question to be added to the database.
     */
    public static void createQuestion(QuestionBO questionBO){
        try
        {
            //Creates query
            String query = "INSERT INTO Question(text) VALUE(?)";
            //Prepares statement and opens connection to the database
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Inserts parameters into prepared statement
            statement.setString(1,questionBO.getText());
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
     * Returns question with inserted ID if exists, otherwise throws EntityInstanceNotFoundException.
     * @param questionID ID of the requested question
     * @return returns question with inserted ID
     */
    public static QuestionBO getQuestion(int questionID){
        try {
            //Creates query
            String query =
                    "SELECT * FROM Question WHERE questionid = ?";
            //Prepares statement and opens connection to the database
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Inserts parameter into prepared statement
            statement.setInt(1, questionID);
            //Executes query and assigns it to a result set
            ResultSet rs = statement.executeQuery();
            //Checks for data
            if( ! rs.next() ) {
                throw new EntityInstanceNotFoundException("There is no question with this id.");
            }
            //Inserts data into QuestionBO instance
            QuestionBO question = new QuestionBO();
            question.setQuestionId(rs.getInt("questionid"));
            question.setText(rs.getString("text"));
            //Closes result set
            rs.close();
            //Closes statement
            statement.close();
            //Closes connection to the database
            DBConnection.disconnect();
            //Returns the question
            return question;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }
    /**
     * Returns list of questions, if none are found returns empty list.
     * @return returns list of all questions.
     */
    public static ArrayList<QuestionBO> getAllQuestions(){
        try {
            //Creates query
            String query =
                    "SELECT * FROM Question";
            //Prepares statement and opens connection to the database
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            //Executes query and assigns it to a result set
            ResultSet rs = statement.executeQuery();
            //Creates returned list instance
            ArrayList <QuestionBO> list = new ArrayList<>();
            //Adds all data into the list
            while (rs.next()) {
                QuestionBO question = new QuestionBO();
                question.setQuestionId(rs.getInt("questionid"));
                question.setText(rs.getString("text"));
                list.add(question);
            }
            //Closes result set
            rs.close();
            //Closes statement
            statement.close();
            //Closes connection to the database
            DBConnection.disconnect();
            //Returns the list of questions
            return list;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }
}