package com.djenterprise.db.game;

import com.djenterprise.app.game.AnswerBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerDAO {
    static final private Logger LOGGER = Logger.getLogger(AnswerDAO.class.getName());

    public static void createAnswer(AnswerBO answer){
        try{
            String query =
                    "INSERT INTO Answer(text, questionid, truthfulness) VALUES (?,?,?)";
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setString(1,answer.getText());
            statement.setInt(2,answer.getQuestionId());
            int truth = (answer.isCorrect()) ? 1 : 0;
            statement.setInt(3, truth);

            statement.execute();
            statement.close();
            DBConnection.disconnect();

        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }

    public static AnswerBO getAnswer(int answerID){
        try {
            String query =
                    "SELECT * FROM ANSWER WHERE answerid = ?";
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setInt(1, answerID);
            ResultSet rs = statement.executeQuery();
            DBConnection.disconnect();
            rs.next();
            if (rs.getInt(("answerid")) == 0) throw new EntityInstanceNotFoundException("Entity not found");
            AnswerBO answer = new AnswerBO();
            answer.setAnswerId(rs.getInt("answerid"));
            answer.setText(rs.getString("text"));
            answer.setTruthfulness(rs.getInt("truthfulness"));
            answer.setQuestionId(rs.getInt("questionid"));
            return answer;

        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }
    }
}
