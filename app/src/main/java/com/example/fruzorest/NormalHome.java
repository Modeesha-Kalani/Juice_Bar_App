package com.example.fruzorest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NormalHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_home);
    }

    public void viewProductHome(View view) {
        startActivity(new Intent(this, ProductHomeActivity.class));
    }

    public void goToOrderHistory(View view) {
        startActivity(new Intent(this, ViewMyOrderActivity.class));
    }

    public void goToReservations(View view) {
        startActivity(new Intent(this, ReservationMenuActivity.class));
    }
}