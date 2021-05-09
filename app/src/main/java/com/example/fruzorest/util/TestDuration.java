package com.example.fruzorest.util;

public class TestDuration {
    private GFG gfg;
    public void setUp(){
        gfg = new GFG();
    }

    public void testDuration(){

        String duration1 = gfg.findDifference("18:30 pm","13.30 pm");
        if (!(duration1=="5")) throw new AssertionError();

        String duration2 = gfg.findDifference("13:45 pm","15.45 pm");
        if (!(duration2=="2")) throw new AssertionError();

        String duration3 = gfg.findDifference("16:00 pm","15.00 pm");
        if (!(duration3=="1")) throw new AssertionError();

    }
}
