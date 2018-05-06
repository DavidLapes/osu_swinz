package com.djenterprise.db.dao;

import com.djenterprise.db.game.GameStateDAO;
import org.apache.xpath.SourceTree;
import org.junit.Assert;
import org.junit.Test;

public class TestLocalTime {

    @Test
    public void test01LocalTime(){
        GameStateDAO.nowToDB("59277633");
        int secsone = GameStateDAO.getStartingTime("59277633").toSecondOfDay();
        try{
            Thread.sleep(3000);
        } catch (InterruptedException ex){

        }
        GameStateDAO.nowToDB("59277633");
        int secstwo = GameStateDAO.getStartingTime("59277633").toSecondOfDay()-3;
        Assert.assertEquals(secsone, secstwo);
    }
}
