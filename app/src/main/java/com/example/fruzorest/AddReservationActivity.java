package com.example.fruzorest;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.fruzorest.model.Reservation;
import com.example.fruzorest.model.Util;
import com.example.fruzorest.util.TimePickerDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReservationActivity extends AppCompatActivity {

    private EditText datetext, timetext, durationtext, reservfor, contact;
    private int tid;
    private SimpleDateFormat sdf;
    private Spinner spinner;
    private String restype = "Standard Reservation";

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(AddReservationActivity.this);
            mProgressDialog.setMessage("Reservation in progress Wait ..");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        datetext = findViewById(R.id.datetext);
        timetext = findViewById(R.id.timetext);
        durationtext = findViewById(R.id.durationtext);
        reservfor = findViewById(R.id.reservfor);
        contact = findViewById(R.id.contactfor);
        spinner = findViewById(R.id.addtype_spinner);

        Intent intent = getIntent();
        sdf = new SimpleDateFormat("yyy-MM-dd");

        tid = intent.getIntExtra("tableid", 0);
        ArrayAdapter<CharSequence> fromResource = ArrayAdapter.createFromResource(this, R.array.reserv_types, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(fromResource);
        AdapterView.OnItemSelectedListener listner = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                restype = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(listner);

    }


    public void openDatePicker(View view) {
        com.example.fruzorest.util.DatePickerDialog datePickerDialog = new com.example.fruzorest.util.DatePickerDialog();
        datePickerDialog.show(getSupportFragmentManager(), "datepicker");
    }

    public void openTimePickcer(View view) {
        TimePickerDialog timepicker = new TimePickerDialog(timetext);
        timepicker.show(getSupportFragmentManager(), "timepicker");
    }

    public void loadTimepickerD(View view) {
        TimePickerDialog timepicker = new TimePickerDialog(durationtext);
        timepicker.show(getSupportFragmentManager(), "timepicker");
    }

    public void makeReservation(View view) {
        showProgressDialog();
        Reservation reserv = new Reservation();
        reserv.setDate(datetext.getText().toString());
        reserv.setTime(timetext.getText().toString());
        reserv.setDuration(durationtext.getText().toString());
        reserv.setContact(contact.getText().toString());
        reserv.setReservfor(reservfor.getText().toString());
        reserv.setUserid(Util.currentuser.getUsername());
        reserv.setStatus("Active");
        reserv.setReservationtype(restype);
        reserv.setReservationdate(sdf.format(new Date()));
        reserv.setTableid(tid);


        DatabaseReference child = FirebaseDatabase.getInstance().getReference("reservation");
        DatabaseReference all = child.child("all").push();
        reserv.setId(all.getKey());
        Task<Void> voidTask = all.setValue(reserv);
        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseReference child1 = child.child("user").child(reserv.getUserid()).child(reserv.getId());
                child1.setValue(reserv).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideProgressDialog();
                        Snackbar snackbar = Snackbar
                                .make(getCurrentFocus(), "New  Reservation added Success !", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        startActivity(new Intent(getApplicationContext(), ReservationDoneActivity.class));
                        finish();

                    }
                });
            }
        });


    }
}