package com.example.sliit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.sql.Time;

public class AddOrder extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, View.OnClickListener {

       private Button btn;
       Spinner mySpinner, mySpinner2;

       EditText etQuantity, tTime;
       AddOrderDetails aod;
       DatabaseReference dbReference;

       private void clearControls(){
           etQuantity.setText("");
           tTime.setText("");
       }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        mySpinner = (Spinner) findViewById(R.id.mySpinner);
        mySpinner.setOnItemSelectedListener(this);

        mySpinner2 = (Spinner) findViewById(R.id.mySpinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(adapter);
        mySpinner2.setOnItemSelectedListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

                case R.id.btn:
                    Toast.makeText(this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
       // Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
