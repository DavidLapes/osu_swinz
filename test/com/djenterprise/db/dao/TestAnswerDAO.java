package com.djenterprise.db.dao;

import com.djenterprise.app.game.AnswerBO;
import com.djenterprise.db.game.AnswerDAO;
import org.junit.Assert;
import org.junit.Test;

public class TestAnswerDAO {

    @Test
    public void test01GetAnswerAnswerID(){
        AnswerBO expected = new AnswerBO();
        expected.setAnswerId(1);
        AnswerBO actual = AnswerDAO.getAnswer(1);
        Assert.assertEquals(actual.getAnswerId(), expected.getAnswerId());
    }

    @Test
    public void test02GetAnswerText(){
        AnswerBO expected = new AnswerBO();
        expected.setText("Death...");
        AnswerBO actual = AnswerDAO.getAnswer(1);
        Assert.assertEquals(actual.getText(), expected.getText());
    }

    @Test
    public void test03GetAnswerQuestionID(){
        AnswerBO expected = new AnswerBO();
        expected.setQuestionId(1);
        AnswerBO actual = AnswerDAO.getAnswer(1);
        Assert.assertEquals(actual.getQuestionId(), expected.getQuestionId());
    }

    @Test
    public void test04GetAnswerTruthfulness(){
        AnswerBO expected = new AnswerBO();
        expected.setTruthfulness(1);
        AnswerBO actual = AnswerDAO.getAnswer(1);
        Assert.assertEquals(actual.isCorrect(), expected.isCorrect());
    }
}
