package com.example.fruzo_juice_bar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Menu2 extends AppCompatActivity {

    Button newRes;
    Button viewRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        Intent intent = getIntent();

        newRes = (Button)findViewById(R.id.newTable);
        viewRes = (Button)findViewById(R.id.viewTable);

        newRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu2.this,getDetails.class);
                startActivity(intent);
            }
        });

        viewRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu2.this,FragmentMain.class);
                startActivity(intent);
            }
        });
    }


}
