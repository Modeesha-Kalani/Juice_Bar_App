package com.example.fruzorest;

import com.example.fruzorest.util.GFG;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeDifferenceTest {

    private GFG gfg;

    @Before
    public void setup(){
        gfg = new GFG();
    }

    @Test
    public void test_getTimeDifference(){
       long result = gfg.getTimeDifference(6,5);
       assertEquals(1, result);
    }

    @Test
    public void test_getTimeDifferenceInHours() {
        long result = gfg.getTimeDifferenceInHours(1, 1000, 60, 60, 24);
        assertEquals(0, result);
    }

}
