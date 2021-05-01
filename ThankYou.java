package com.example.fruzo_juice_bar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ThankYou extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
