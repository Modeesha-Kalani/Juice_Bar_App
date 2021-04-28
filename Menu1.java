package com.example.fruzo_juice_bar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Menu1 extends AppCompatActivity {

    Button order;
    Button reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);



        order = findViewById(R.id.OrderJuice);
        reserve = findViewById(R.id.ReserveTable);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu1.this,"Go to Order Juice Page",Toast.LENGTH_SHORT).show();
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu1.this,Menu2.class);
                startActivity(intent);
            }
        });

    }
}
