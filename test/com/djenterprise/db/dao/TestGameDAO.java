package com.djenterprise.db.dao;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.app.game.QuestionBO;
import com.djenterprise.db.game.GameDAO;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestGameDAO {

    @Test
    public void test01GetGameCreator() {
        GameBO actual;
        actual = GameDAO.getGame("47310824");

        GameBO expected = new GameBO();
        expected.setCreator("David");
        expected.setGameId("47310824");

        Assert.assertEquals(expected.getCreator(), actual.getCreator());
    }

    @Test
    public void test02GetGameQuestions() {
        GameBO game = GameDAO.getGame("47310824");
        List<QuestionBO> list = GameDAO.getQuestions(game);
        int expected = 4;
        int actual = list.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test03CreateGame() {
        GameBO game = new GameBO();
        game.generateId();
        System.out.println(game.getGameId());
    }

    @Test
    public void test04GetGamesByDateCreated() {
        int timeInSeconds = 1;
        List<GameBO> list = GameDAO.getGamesOlderThan(timeInSeconds);
        for( GameBO x : list ) {
            System.out.println(x.getGameId() + ", " + x.getCreator() + ", " + x.getDateCreated().toString());
        }
    }

    @Test
    public void test05DeleteGamesOlderThan() {
        int timeInSeconds = 1;
        List<GameBO> list = GameDAO.getGamesOlderThan(timeInSeconds);
        for( GameBO x : list ) {
            GameDAO.deleteGame(x.getGameId());
        }
    }
}