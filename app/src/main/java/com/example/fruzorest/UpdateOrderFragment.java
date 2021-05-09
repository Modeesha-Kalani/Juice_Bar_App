package com.example.fruzorest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fruzorest.model.Order;
import com.example.fruzorest.model.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;


public class UpdateOrderFragment extends Fragment {

    private Bundle bundle;
    private TextView total, pname, rprice, lprice, rqty, lqty;
    private Button rinbtn, rdebtn, linbtn, ldebtn, updateorderbtn;
    private TimePicker timepicker;
    private double rpriceof, lpriceof, tot;
    private int rqtyof, larqtyof;
    private View.OnClickListener rinlistner;
    private View.OnClickListener rdelistner;
    private View.OnClickListener linlistner;
    private View.OnClickListener ldelistner;
    private View.OnClickListener updatelistner;
    private Order o;


    public UpdateOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = getArguments();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitApp();

    }

    private void InitApp() {
        total = getView().findViewById(R.id.pu_tot);
        pname = getView().findViewById(R.id.pu_pname);
        rprice = getView().findViewById(R.id.pu_regprice);
        lprice = getView().findViewById(R.id.pu_largeprice);
        rqty = getView().findViewById(R.id.puinteger_number);
        lqty = getView().findViewById(R.id.puinteger_number2);
        timepicker = getView().findViewById(R.id.timePicker2);
        rinbtn = getView().findViewById(R.id.puincrease);
        rdebtn = getView().findViewById(R.id.pudecrease);
        linbtn = getView().findViewById(R.id.puincrease2);
        ldebtn = getView().findViewById(R.id.pudecrease2);
        updateorderbtn = getView().findViewById(R.id.updateorderbtn);
        //set Listners to btns
        rinlistner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rqtyof++;
                rqty.setText(rqtyof + "");
                updateTot();
            }
        };
        rinbtn.setOnClickListener(rinlistner);

        rdelistner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rqtyof != 0) {
                    rqtyof--;
                }
                rqty.setText(rqtyof + "");
                updateTot();
            }
        };
        rdebtn.setOnClickListener(rdelistner);

        linlistner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                larqtyof++;
                lqty.setText(larqtyof + "");
                updateTot();
            }
        };
        linbtn.setOnClickListener(linlistner);

        ldelistner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (larqtyof != 0) {
                    larqtyof--;
                }
                lqty.setText(larqtyof + "");
                updateTot();
            }
        };
        ldebtn.setOnClickListener(ldelistner);

        String orderidof = bundle.getString("orderid");
        DatabaseReference child = FirebaseDatabase.getInstance().getReference("order").child("allorder").child(orderidof);
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Order value = snapshot.getValue(Order.class);
                o = value;
                pname.setText(value.getPname());
                rqty.setText("Rs." + value.getRqty());
                lqty.setText("Rs." + value.getLqty());
                total.setText(value.getTotal() + "");
                rprice.setText(value.getRprice() + "");
                lprice.setText(value.getLprice() + "");
                timepicker.setHour(value.getHour());
                timepicker.setMinute(value.getMinuts());
                rpriceof = value.getRprice();
                rqtyof = value.getRqty();
                lpriceof = value.getLprice();
                larqtyof = value.getLqty();
                tot = value.getTotal();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        updatelistner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                o.setRqty(rqtyof);
                o.setLqty(larqtyof);
                o.setTotal(tot);
                o.setRprice(rpriceof);
                o.setLprice(lpriceof);
                int hour = timepicker.getHour();
                int minute = timepicker.getMinute();
                o.setHour(hour);
                o.setMinuts(minute);

                DatabaseReference order = FirebaseDatabase.getInstance().getReference("order");
                DatabaseReference allorder = order.child("allorder").child(o.getId());

                Task<Void> voidTask = allorder.setValue(o);
                voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DatabaseReference userorder = order.child("userorder").child(o.getUserid()).child(o.getId());
                        Task<Void> voidTask1 = userorder.setValue(o);
                        voidTask1.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Order Updated", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


            }
        };
        updateorderbtn.setOnClickListener(updatelistner);

    }

    private void updateTot() {
        double rtot = rpriceof * rqtyof;
        double ltot = lpriceof * larqtyof;
        tot = rtot + ltot;
        total.setText(tot + "");
    }
}