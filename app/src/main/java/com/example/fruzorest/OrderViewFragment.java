package com.example.fruzorest;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fruzorest.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.glxn.qrgen.android.QRCode;


public class OrderViewFragment extends Fragment {
    private Bundle bundle;

    //components
    private TextView pname, rqty, lqty, pickuptime, total, orderid;
    private ImageView qrimg;

    public OrderViewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = getArguments();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initApp();

    }

    private void initApp() {
        pname = getView().findViewById(R.id.ovf_name);
        rqty = getView().findViewById(R.id.ovf_regqty);
        lqty = getView().findViewById(R.id.ovf_lqty);
        total = getView().findViewById(R.id.ovf_tot);
        pickuptime = getView().findViewById(R.id.ovf_pickuptime);
        qrimg = getView().findViewById(R.id.ovf_qr);
        orderid = getView().findViewById(R.id.ovf_orderid);
        String orderidof = bundle.getString("orderid");

        //firebase connection
        /* flow of the database
        * Firebase > order > all order - if you want to view all orders you can stop it here
        *Firebase > order > all order > orderidof - if you want to view one of orders
        */
        DatabaseReference child = FirebaseDatabase.getInstance().getReference("order").child("allorder").child(orderidof);
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Order value = snapshot.getValue(Order.class);
                pname.setText(value.getPname());
                rqty.setText("Rs."+value.getRprice()+" X "+value.getRqty());
                lqty.setText("Rs."+value.getLprice()+" X "+value.getLqty());
                total.setText(value.getTotal()+"");
                String timex = "";

                //to convert time into 12 hours
                if(value.getHour()>12){
                    timex = (value.getHour()-12)+":"+value.getMinuts()+" PM";
                }else{
                    timex = value.getHour()+":"+value.getMinuts()+" AM";
                }
                pickuptime.setText(timex);
                orderid.setText(value.getId()); //set the order id
                loadQR(value.getId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    // to load QR code
    private void loadQR(String id) {
        Bitmap bitmap = QRCode.from(id).bitmap();
        qrimg.setImageBitmap(bitmap);

    }
}