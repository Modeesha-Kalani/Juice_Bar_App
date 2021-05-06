package com.example.fruzorest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    public void addAdmin(View view) {
        startActivity(new Intent(this, AddAdminActivity.class));
    }

    public void viewAdmins(View view) {
        startActivity(new Intent(this, AdminViewActivity.class));
    }

    public void addMenu(View view) {
        startActivity(new Intent(this,AddProductActivity.class));
    }

    public void viewMenu(View view) {
        startActivity(new Intent(this,ViewProductAdminActivity.class));
    }
}