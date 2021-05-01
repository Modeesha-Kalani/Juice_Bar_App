package com.example.sliit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ViewOrder extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    private Button btn2, btn3;
    Spinner mySpinner3, mySpinner4;
    ImageView imageView15;
    TextView textView22;

    String data1;
    int myImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        mySpinner3 = (Spinner) findViewById(R.id.mySpinner3);
        mySpinner3.setOnItemSelectedListener(this);

        mySpinner4 = (Spinner) findViewById(R.id.mySpinner4);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner4.setAdapter(adapter);
        mySpinner4.setOnItemSelectedListener(this);

        imageView15 =  findViewById(R.id.imageView15);
        textView22 = findViewById(R.id.textView22);

        getData();
        setData();
    }
    private void getData(){
        if(getIntent().hasExtra("myImageView") && getIntent().hasExtra("data1")){
                data1 = getIntent().getStringExtra("data1");
                myImage = getIntent().getIntExtra("myImage", 1);
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(){
        textView22.setText(data1);
        imageView15.setImageResource(myImage);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btn2:
                Toast.makeText(this, "Order Updated Successfully", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn3:
                Toast.makeText(this, "Order Deleted Successfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}