package com.djenterprise.db.game;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TestScheduledTask {

    @Test
    public void test01PeriodicalRun() throws Exception {
        Timer timer = new Timer();
        TimerTask timerTask = new ScheduledTask();
        timer.scheduleAtFixedRate(timerTask, 3000, 1000);
        /*
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        while(true) {

        }
        /*
        for( int i = 0; i <= 5; i ++ ) {
            System.out.println("Execution in Main Thread...: " + i);
            //Thread.sleep(1000);
            if( i == 5 ) {
                System.out.println("Application terminates");
                //System.exit(0);
            }
        }
        */
    }
}