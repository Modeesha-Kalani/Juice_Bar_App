package com.example.fruzorest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class TableActivity extends AppCompatActivity {
    private LinearLayout t1, t2, t3, t4, t5, t6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        t1 = findViewById(R.id.card_t1);
        t2 = findViewById(R.id.card_t2);
        t3 = findViewById(R.id.card_t3);
        t4 = findViewById(R.id.card_t4);
        t5 = findViewById(R.id.card_t5);
        t6 = findViewById(R.id.card_t6);

    }

    public void reservTable01(View view) {
        goToAddReservation(1);
    }

    public void reservTable02(View view) {
        goToAddReservation(2);
    }

    public void reservTable03(View view) {
        goToAddReservation(3);
    }

    public void reservTable04(View view) {
        goToAddReservation(4);
    }

    public void reservTable05(View view) {
        goToAddReservation(5);
    }

    public void reservTable06(View view) {
        goToAddReservation(6);
    }

    private void goToAddReservation(int table) {
        Intent i = new Intent(this, AddReservationActivity.class);
        i.putExtra("tableid", table);
        startActivity(i);
    }
}
