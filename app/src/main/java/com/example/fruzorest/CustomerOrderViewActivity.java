package com.example.fruzorest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.fruzorest.model.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerOrderViewActivity extends AppCompatActivity {
    private String orderid;
    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_view);
        Intent intent = getIntent();
        orderid = intent.getStringExtra("orderid");
        manager = getSupportFragmentManager();
        loadFragment("details");
    }

    private void loadFragment(String details) {
        FragmentTransaction ft = manager.beginTransaction();
        if (details == "details") {

            Bundle bundle = new Bundle();
            bundle.putString("orderid", orderid);
            OrderViewFragment orderViewFragment = new OrderViewFragment();
            orderViewFragment.setArguments(bundle);
            ft.replace(R.id.ovc_framelayout, orderViewFragment);
            ft.commit();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("orderid", orderid);
            UpdateOrderFragment updateOrderFragment = new UpdateOrderFragment();
            updateOrderFragment.setArguments(bundle);
            ft.replace(R.id.ovc_framelayout, updateOrderFragment);
            ft.commit();
        }


    }

    public void goToUpdateOrder(View view) {
        loadFragment("update");
    }

    public void deleteOrder(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerOrderViewActivity.this);
        builder.setMessage("Do You want to Cancel this Order ? ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        DatabaseReference child = FirebaseDatabase.getInstance()
                                .getReference("order")
                                .child("allorder")
                                .child(orderid);

                        Task<Void> voidTask = child.removeValue();
                        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                DatabaseReference userorder = FirebaseDatabase.getInstance().getReference("order")
                                        .child("userorder")
                                        .child(Util.currentuser.getUsername())
                                        .child(orderid);

                                Task<Void> voidTask2 = userorder.removeValue();
                                voidTask2.addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Order Canceled",
                                                Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                        finish();
                                    }
                                });

                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Cancel Order");
        alert.show();
    }
}