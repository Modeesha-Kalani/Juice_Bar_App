package com.example.fruzorest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReservationMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_menu);
    }

    public void goToNewReservation(View view) {
        startActivity(new Intent(this, TableActivity.class));
    }

    public void goToViewReservation(View view) {
        startActivity(new Intent(this, ViewReservationActivity.class));
    }
}
