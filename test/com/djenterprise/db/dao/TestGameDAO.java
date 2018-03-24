package com.djenterprise.db.dao;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.db.game.GameDAO;
import org.junit.Assert;
import org.junit.Test;

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
}