package com.example.fruzorest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }

    public void goToAdminMenu(View view) {
        startActivity(new Intent(this,AdminMenuActivity.class));
    }

    public void goToCustomerHome(View view) {
        startActivity(new Intent(this,NormalHome.class));
    }
}