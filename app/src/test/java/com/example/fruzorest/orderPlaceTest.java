package com.example.fruzorest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class orderPlaceTest {

    private OrderPlaceActivity orderPlaceActivity;

    @Before
    public void setup(){
        orderPlaceActivity = new OrderPlaceActivity();
    }

    @Test
    public void updateTot_isCorrect(){

        double result = orderPlaceActivity.updateTot((double)1,1,1,1);
        assertEquals(2,result,0.001);

    }

}
