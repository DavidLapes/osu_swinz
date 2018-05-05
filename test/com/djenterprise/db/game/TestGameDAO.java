package com.djenterprise.db.game;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.app.game.QuestionBO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestGameDAO {

    static final private String PLAYER_ONE_ALIAS = "David";
    static final private String PLAYER_TWO_ALIAS = "Chymja";
    static final private String CREATOR_USERNAME = "David";
    static final private String GAME_ID = "01234567";

    @Test
    public void test01CreateGame() {
        GameBO game = new GameBO();
        game.setPlayerOne(PLAYER_ONE_ALIAS);
        game.setPlayerTwo(PLAYER_TWO_ALIAS);
        game.setCreator(CREATOR_USERNAME);
        game.setGameId(GAME_ID);
        List<QuestionBO> questionBOList = QuestionDAO.getAllQuestions();
        List<QuestionBO> questions = new ArrayList<>();
        questions.add(questionBOList.get(0));
        questions.add(questionBOList.get(1));
        GameDAO.createGame(game, questions);
    }

    @Test
    public void test02DeleteGame() {
        GameDAO.deleteGame(GAME_ID);
    }
}