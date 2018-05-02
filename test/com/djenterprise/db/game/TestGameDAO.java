package com.djenterprise.db.game;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.app.game.QuestionBO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestGameDAO {

    @Test
    public void test01CreateGame() {
        GameBO game = new GameBO();
        game.setPlayerOne("David");
        game.setPlayerTwo("Chymja");
        game.setCreator("David");
        game.setGameId(game.generateId());
        List<QuestionBO> questionBOList = QuestionDAO.getAllQuestions();
        List<QuestionBO> questions = new ArrayList<>();
        questions.add(questionBOList.get(0));
        questions.add(questionBOList.get(1));
        GameDAO.createGame(game, questions);
    }
}