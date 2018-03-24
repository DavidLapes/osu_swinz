package com.djenterprise.db.game;

import com.djenterprise.db.connection.DBConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

//TODO JavaDoC
//TODO Comments
public class QuestionDAO {

    static final private Logger LOGGER = Logger.getLogger(QuestionDAO.class.getName());

    public static void createQuestion(String text){
        if (text == null || text.isEmpty()) return;
        try
        {
            String query = "INSERT INTO Question(text) VALUE(?)";
            PreparedStatement statement = DBConnection.connect().prepareStatement(query);
            statement.setString(1,text);
            statement.execute();
            statement.close();
            DBConnection.disconnect();
        } catch (SQLException SQLEx) {
            LOGGER.error(SQLEx);
            throw new RuntimeException(SQLEx);
        }

    }
}