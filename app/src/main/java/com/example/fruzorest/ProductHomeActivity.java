package com.example.fruzorest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProductHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_home);
    }

    public void viewJuices(View view) {
        Intent i = new Intent(this, ProductCViewActivity.class);
        i.putExtra("type","juice");
        startActivity(i);
    }

    public void viewSalads(View view) {
        Intent i = new Intent(this, ProductCViewActivity.class);
        i.putExtra("type","salad");
        startActivity(i);
    }

    public void viewSmoothis(View view) {
        Intent i = new Intent(this, ProductCViewActivity.class);
        i.putExtra("type","smoothi");
        startActivity(i);
    }
}