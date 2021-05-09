package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fruzorest.model.Order;
import com.example.fruzorest.model.Product;
import com.example.fruzorest.model.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderPlaceActivity extends AppCompatActivity {

    private TextView regqty, largeqty, ingredients, total, regprice, largeprice, pname;
    private ImageView imageView;
    private TimePicker timepicker;

    private double rprice, lprice, tot;
    private int rqty, larqty;
    private String pid;
    private String type;
    private SimpleDateFormat sdf;
    private AlertDialog.Builder builder;
    private Product p;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(OrderPlaceActivity.this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_place);

        regqty = findViewById(R.id.integer_number);
        largeqty = findViewById(R.id.integer_number2);
        regprice = findViewById(R.id.po_regprice);
        largeprice = findViewById(R.id.po_largeprice);
        ingredients = findViewById(R.id.po_ingredients);
        total = findViewById(R.id.po_tot);
        pname = findViewById(R.id.po_pname);
        imageView = findViewById(R.id.po_imgview);
        timepicker = findViewById(R.id.timePicker1);
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        type = intent.getStringExtra("type");
        sdf = new SimpleDateFormat("yyy-MM-dd HH:mm a");

        loadData(pid, type);

    }

    private void loadData(String pid, String type) {
        DatabaseReference products = FirebaseDatabase.getInstance().getReference("products").child(type).child(pid);
        products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product value = snapshot.getValue(Product.class);
                p = value;
                pname.setText(value.getName());
                regprice.setText("Rs."+value.getReg_price() + "");
                rprice = value.getReg_price();
                largeprice.setText("Rs."+value.getLarge_price() + "");
                lprice = value.getLarge_price();
                ingredients.setText(value.getIngredients());
                StorageReference products1 = FirebaseStorage.getInstance().getReference("products").child(value.getId());
                products1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext()).load(uri).into(imageView);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void minRegQty(View view) {
        if (rqty != 0) {
            rqty--;
        }
        regqty.setText(rqty + "");
        double v = updateTot(rprice, rqty, lprice, larqty);
        total.setText(v + "");
    }

    public void maxRegQty(View view) {
        rqty++;
        regqty.setText(rqty + "");
        double v = updateTot(rprice, rqty, lprice, larqty);
        total.setText(v + "");
    }

    public void minLQty(View view) {
        if (larqty != 0) {
            larqty--;
        }
        largeqty.setText(larqty + "");
        double v = updateTot(rprice, rqty, lprice, larqty);
        total.setText(v + "");
    }

    public void maxLQty(View view) {
        larqty++;
        largeqty.setText(larqty + "");
        double v = updateTot(rprice, rqty, lprice, larqty);
        total.setText(v + "");
    }

    private double updateTot(double rprice, int rqty, double lprice, int larqty) {
        double rtot = rprice * rqty;
        double ltot = lprice * larqty;
        tot = rtot + ltot;
        return tot;

    }
//    private void updateTot() {
//        double rtot = rprice * rqty;
//        double ltot = lprice * larqty;
//        tot = rtot + ltot;
//        total.setText(tot + "");
//    }


    public void makeOrder(View view) {
        showProgressDialog();
        Order o = new Order();
        o.setPid(pid);
        o.setPtype(type);
        o.setRqty(rqty);
        o.setLqty(larqty);
        o.setTotal(tot);
        o.setRprice(rprice);
        o.setLprice(lprice);
        o.setStatus(0);
        o.setPname(p.getName());
        o.setUserid(Util.currentuser.getUsername());
        o.setDate(sdf.format(new Date()));
        int hour = timepicker.getHour();
        int minute = timepicker.getMinute();
        o.setHour(hour);
        o.setMinuts(minute);

        DatabaseReference order = FirebaseDatabase.getInstance().getReference("order");
        DatabaseReference allorder = order.child("allorder").push();
        o.setId(allorder.getKey());
        Task<Void> voidTask = allorder.setValue(o);
        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseReference userorder = order.child("userorder").child(o.getUserid()).child(o.getId());
                Task<Void> voidTask1 = userorder.setValue(o);
                voidTask1.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideProgressDialog();
                        showNotification(o);
                        builder = new AlertDialog.Builder(OrderPlaceActivity.this);
                        builder.setMessage("")
                                .setCancelable(false)
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        onBackPressed();
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
                        alert.setTitle("New Order Placed Success ");
                        alert.show();
                    }
                });
                hideProgressDialog();
            }
        });


    }

    public void showNotification(Order o) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("orderplaced", name, importance);
            channel.setDescription(description);
// Register the channel with the system; you can't change the importance
// or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(this, ViewMyOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "orderplaced")
                .setSmallIcon(R.drawable.ic_baseline_favorite_24)
                .setContentTitle("New Order Placed")
                .setContentText(o.getPname() + " \n" + o.getTotal())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
// Set the intent that will fire when the user  taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
// notificationId is a unique int for each notification that you  must define
        notificationManager.notify(0, builder.build());

    }

}