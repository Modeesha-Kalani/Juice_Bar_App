package com.example.sliit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn5, btn6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        
       btn5.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent int1 = new Intent(MainActivity.this, AddOrder.class);
               startActivity(int1);
           }
       });

       btn6.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               Intent int2 = new Intent(MainActivity.this, OrderHistory.class);
               startActivity(int2);
           }
       });
    }



}