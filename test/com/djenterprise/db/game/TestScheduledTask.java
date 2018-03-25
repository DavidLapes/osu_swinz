package com.djenterprise.db.game;

import com.djenterprise.app.game.ScheduledTask;
import org.junit.Test;

import java.util.Properties;

public class TestScheduledTask {

    @Test
    public void test01GetResources() {
        Properties properties = ScheduledTask.getResources();
        System.out.println(properties.getProperty("time"));
    }
}