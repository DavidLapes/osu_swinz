package com.djenterprise.db.dao;

import com.djenterprise.app.game.QuestionBO;
import com.djenterprise.db.game.QuestionDAO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestQuestionDAO {
    //TODO
    /*
    @Test
    public void test01CreateQuestion(){
    }
    */
    @Test
    public void test02GetQuestionQuestionID(){
        QuestionBO expected = new QuestionBO();
        expected.setQuestionId(1);
        QuestionBO actual = QuestionDAO.getQuestion(1);
        Assert.assertEquals(actual.getQuestionId(), expected.getQuestionId());
    }
    @Test
    public void test03GetQuestionQuestionText(){
        QuestionBO expected = new QuestionBO();
        expected.setText("What is the music of life?");
        QuestionBO actual = QuestionDAO.getQuestion(1);
        Assert.assertEquals(actual.getText(), expected.getText());
    }
    @Test
    public void test04GetQuestionsPos1Text(){
        ArrayList<QuestionBO> expected = new ArrayList<>();
        expected.add(new QuestionBO());
        expected.get(0).setText("Stormcloacks, or Imperials?");
        List<QuestionBO> actual = QuestionDAO.getAllQuestions();
        Assert.assertEquals(actual.get(1).getText(), expected.get(0).getText());
    }
    @Test
    public void test05GetQuestionsPos2Text(){
        ArrayList<QuestionBO> expected = new ArrayList<>();
        expected.add(new QuestionBO());
        expected.get(0).setText("Who is Astrid?");
        List<QuestionBO> actual = QuestionDAO.getAllQuestions();
        Assert.assertEquals(actual.get(2).getText(), expected.get(0).getText());
    }
    @Test
    public void test06GetQuestionsPos3Text(){
        ArrayList<QuestionBO> expected = new ArrayList<>();
        expected.add(new QuestionBO());
        expected.get(0).setText("Shall we execute Cicero?");
        List<QuestionBO> actual = QuestionDAO.getAllQuestions();
        Assert.assertEquals(actual.get(3).getText(), expected.get(0).getText());
    }
    @Test
    public void test07GetQuestionsPos4Text(){
        ArrayList<QuestionBO> expected = new ArrayList<>();
        expected.add(new QuestionBO());
        expected.get(0).setText("Is Ulrfic the High King?");
        List<QuestionBO> actual = QuestionDAO.getAllQuestions();
        Assert.assertEquals(actual.get(4).getText(), expected.get(0).getText());
    }


}
