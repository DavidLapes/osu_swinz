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

    public static void createQuestion(QuestionBO questionBO){
        if (questionBO.getText() == null || questionBO.getText().isEmpty()) return;
        try
        {
            String query = "INSERT INTO Question(text) VALUE(?)";
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setString(1,questionBO.getText());
            statement.execute();
            statement.close();
            DBConnection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    public static QuestionBO getQuestion(int questionID){
        try {
            String query =
                    "SELECT * FROM Question WHERE questionid = ?";
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setInt(1, questionID);
            ResultSet rs = statement.executeQuery();
            if( ! rs.next() ) {
                throw new EntityInstanceNotFoundException("There is no question with this id.");
            }
            QuestionBO question = new QuestionBO();
            question.setQuestionId(rs.getInt("questionid"));
            question.setText(rs.getString("text"));
            statement.close();
            DBConnection.disconnect();
            return question;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    public static ArrayList<QuestionBO> getAllQuestions(){
        try {
            String query =
                    "SELECT * FROM Question";
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            ArrayList <QuestionBO> list = new ArrayList<>();
            while (rs.next()) {
                QuestionBO question = new QuestionBO();
                question.setQuestionId(rs.getInt("questionid"));
                question.setText(rs.getString("text"));
                list.add(question);
            }
            statement.close();
            DBConnection.disconnect();
            return list;
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }
}