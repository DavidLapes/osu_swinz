package com.djenterprise.db.game;

import com.djenterprise.app.game.AnswerBO;
import com.djenterprise.app.game.QuestionBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    //Variable for logging
    static final private Logger LOGGER = Logger.getLogger(QuestionDAO.class.getName());

    /**
     * Inserts a question into the database
     *
     * @param questionBO question to be added to the database.
     */
    @Deprecated
    public static void createQuestion(QuestionBO questionBO) {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            //Creates query
            String query = "INSERT INTO Question(text) VALUE(?)";
            //Prepares statement and opens connection to the database
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            //Inserts parameters into prepared statement
            statement.setString(1, questionBO.getText());
            //Executes SQL command
            statement.execute();
            //Closes statement
            statement.close();
            //Closes connection to the database
            connection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Returns question with inserted ID if exists, otherwise throws EntityInstanceNotFoundException.
     *
     * @param questionID ID of the requested question
     * @return returns question with inserted ID
     * @throws EntityInstanceNotFoundException when no question with input ID has been found
     */
    public static QuestionBO getQuestion(int questionID) {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            //Creates query
            String query =
                    "SELECT * FROM Question WHERE questionid = ?";
            //Prepares statement and opens connection to the database
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            //Inserts parameter into prepared statement
            statement.setInt(1, questionID);
            //Executes query and assigns it to a result set
            ResultSet rs = statement.executeQuery();
            //Checks for data
            if (!rs.next()) {
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
            connection.disconnect();
            //Returns the question
            return question;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    /**
     * Returns list of questions, if none are found returns empty list.
     *
     * @return returns list of all questions.
     */
    public static List<QuestionBO> getAllQuestions() {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            //Creates query
            String query =
                    "SELECT * FROM Question";
            //Prepares statement and opens connection to the database
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            //Executes query and assigns it to a result set
            ResultSet rs = statement.executeQuery();
            //Creates returned list instance
            List<QuestionBO> list = new ArrayList<>();
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
            connection.disconnect();
            //Returns the list of questions
            return list;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    public static void fillAnswersToQuestion(QuestionBO question) {
        try {
            //Connect to DB
            DBConnection connection = new DBConnection();
            connection.connect();
            //Creates query
            String query =
                    "SELECT answerid, text FROM Answer WHERE questionid = ?";
            //Prepares statement and opens connection to the database
            PreparedStatement statement = connection.getCONNECTION().prepareStatement(query);
            statement.setInt(1, question.getQuestionId());
            //Executes query and assigns it to a result set
            ResultSet rs = statement.executeQuery();
            //Adds all data into the list
            while (rs.next()) {
                AnswerBO answer = new AnswerBO();
                answer.setAnswerId(rs.getInt("answerid"));
                answer.setText(rs.getString("text"));
                question.addAnswer(answer);
            }
            //Closes result set
            rs.close();
            //Closes statement
            statement.close();
            //Closes connection to the database
            connection.disconnect();
        } catch(SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
}
}