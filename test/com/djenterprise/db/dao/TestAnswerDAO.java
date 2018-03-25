package com.djenterprise.db.dao;

import com.djenterprise.app.game.AnswerBO;
import com.djenterprise.db.connection.DBConnection;
import com.djenterprise.db.game.AnswerDAO;
import org.junit.Assert;
import org.junit.Test;

public class TestAnswerDAO {

    @Test
    public void test01GetAnswerAnswerID(){
        AnswerBO expected = new AnswerBO();
        expected.setAnswerId(1);
        AnswerBO actual = AnswerDAO.getAnswer(1);
        Assert.assertEquals(expected.getAnswerId(), actual.getAnswerId());
    }

    @Test
    public void test02GetAnswerText(){
        AnswerBO expected = new AnswerBO();
        expected.setText("Death...");
        AnswerBO actual = AnswerDAO.getAnswer(1);
        Assert.assertEquals(expected.getText(), actual.getText());
    }

    @Test
    public void test03GetAnswerQuestionID(){
        AnswerBO expected = new AnswerBO();
        expected.setQuestionId(1);
        AnswerBO actual = AnswerDAO.getAnswer(1);
        Assert.assertEquals(expected.getQuestionId(), actual.getQuestionId());
    }

    @Test
    public void test04GetAnswerTruthfulness(){
        AnswerBO expected = new AnswerBO();
        expected.setTruthfulness(1);
        AnswerBO actual = AnswerDAO.getAnswer(1);
        Assert.assertEquals(expected.isCorrect(), actual.isCorrect());
    }

    @Test //Note: Requires test01CreateQuestion to be run beforehand to function correctly
    public void test05CreateAnswer(){
        DBConnection.initialize();
        AnswerBO expected = new AnswerBO();
        expected.setText("This is a test");
        AnswerBO question = new AnswerBO();
        question.setText("This is a test");
        question.setQuestionId(6);
        question.setTruthfulness(0);
        AnswerDAO.createAnswer(question);
        AnswerBO actual = AnswerDAO.getAnswer(21);
        Assert.assertEquals(expected.getText(), actual.getText());
    }
}
