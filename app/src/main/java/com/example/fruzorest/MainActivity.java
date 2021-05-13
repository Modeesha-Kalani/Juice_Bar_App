package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.fruzorest.model.User;
import com.example.fruzorest.model.Util;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText pass;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pass = findViewById(R.id.password_tf);
        username = findViewById(R.id.username_login);


    }

    public void logIn(View view) {

        if (username.getText().toString().isEmpty()|| pass.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Dialog_MinWidth);
            builder.setTitle("Cannot Login");
            builder.setMessage("Empty Username or password").setCancelable(false);
            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    hideProgressDialog();
                }
            }).setIcon(R.drawable.ic_baseline_error_outline_24);
            builder.create().show();
        } else {
            showProgressDialog();
            FirebaseDatabase instance = FirebaseDatabase.getInstance();
            DatabaseReference child = instance.getReference("user").child("allusers").child(username.getText().toString());
            child.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User value = snapshot.getValue(User.class);
                    if (value != null) {
                        if (value.getPassword().equals(pass.getText().toString())) {

                            Util.currentuser = value;
                            if (value.getUserlevel() == 1 || value.getUserlevel() == 2) {
                                startActivity(new Intent(getApplicationContext(), AdminHome.class));
                                finish();
                            } else {
                                startActivity(new Intent(getApplicationContext(), NormalHome.class));
                                finish();
                            }

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Dialog_MinWidth);
                            builder.setTitle("Cannot Login");
                            builder.setMessage("Wrong Password !").setCancelable(false);
                            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    hideProgressDialog();
                                }
                            }).setIcon(R.drawable.ic_baseline_error_outline_24);
                            builder.create().show();

                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Dialog_MinWidth);
                        builder.setTitle("Cannot Login");
                        builder.setMessage("Cannot Find User !").setCancelable(false);
                        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                hideProgressDialog();
                            }
                        }).setIcon(R.drawable.ic_baseline_error_outline_24);
                        builder.create().show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    public void newUser(View view) {
        startActivity(new Intent(this, NewUserActivity.class));
    }

    public void forgotPassword(View view) {


    }

    //progressbar
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Log in .....");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
}