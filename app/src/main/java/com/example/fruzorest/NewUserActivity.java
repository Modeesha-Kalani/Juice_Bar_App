package com.example.fruzorest;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewUserActivity extends AppCompatActivity {

    //components
    private EditText uname;
    private EditText email;
    private EditText mobile;
    private EditText password;
    private EditText repassword;

    //Firebase
    FirebaseDatabase firebase;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        uname = findViewById(R.id.uname_newuser);
        email = findViewById(R.id.email_newuser);
        mobile = findViewById(R.id.mobile_newuser);
        password = findViewById(R.id.password_newuser);
        repassword = findViewById(R.id.repassword_newuser);
        firebase = FirebaseDatabase.getInstance();

    }

    public void createNewAccount(View view) {
        showProgressDialog();
        if (email.getText().toString().isEmpty() ||
                mobile.getText().toString().isEmpty() ||
                uname.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty() ||
                repassword.getText().toString().isEmpty()) {
//check values
            hideProgressDialog();
            AlertDialog.Builder builder = new AlertDialog.Builder(NewUserActivity.this, R.style.Theme_AppCompat_Dialog_MinWidth);
            builder.setTitle("Cannot Create User Account");
            builder.setMessage("Fill All textfields").setCancelable(false);
            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    hideProgressDialog();
                }
            }).setIcon(R.drawable.ic_baseline_error_outline_24);
            builder.create().show();

        } else {
            if (password.getText().toString().equals(repassword.getText().toString())) {
                User user = new User();
                user.setEmail(email.getText().toString());
                user.setMobile(mobile.getText().toString());
                user.setUsername(uname.getText().toString());
                user.setFirstname("");
                user.setLastname("");
                user.setNic("");
                user.setPassword(password.getText().toString());
                user.setStatus(1);
                user.setRole("Customer");
                user.setUserlevel(3);


                DatabaseReference userref = firebase.getReference("user");
                DatabaseReference allusers = userref.child("allusers").child(user.getUsername());

                Task<Void> push = allusers.setValue(user);
                push.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideProgressDialog();
                        Snackbar snackbar = Snackbar
                                .make(getCurrentFocus(), "New User Account Created Success !", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Util.currentuser = user;
                        startActivity(new Intent(getApplicationContext(), NormalHome.class));
                    }


                });
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewUserActivity.this, R.style.Theme_AppCompat_Dialog_MinWidth);
                builder.setTitle("Cannot Create User Account");
                builder.setMessage("Password doesn't match").setCancelable(false);
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
    }

    public void goToLogin(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    //progressbar
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(NewUserActivity.this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
}