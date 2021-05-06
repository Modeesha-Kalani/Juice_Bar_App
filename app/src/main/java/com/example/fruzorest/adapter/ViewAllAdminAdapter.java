package com.example.fruzorest.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruzorest.AdminDetailActivity;
import com.example.fruzorest.AdminViewActivity;
import com.example.fruzorest.R;
import com.example.fruzorest.model.UserInfo;
import com.example.fruzorest.viewholder.ViewAllAdminViewHolder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewAllAdminAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    ArrayList<UserInfo> list;
    Context context;
    View.OnClickListener clickListener;
    AlertDialog.Builder builder;

    public ViewAllAdminAdapter(ArrayList<UserInfo> list) {
        this.list = list;

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(context, AdminDetailActivity.class);
        Bundle b = new Bundle();
        int pos = (int) v.findViewById(R.id.ar_viewbtn).getTag();
        i.putExtra("admin", list.get(pos).getUser().getUsername());
        // System.out.println(list.get(pos).getShop().getAddress());
        context.startActivity(i);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminrecycle, parent, false);
        builder = new AlertDialog.Builder(context);
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage(R.string.dialogmessag).setTitle(R.string.deleteadmin);

                builder.setMessage("Do You want to delete This Account ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int tag = (int) v.getTag();
                                DatabaseReference child = FirebaseDatabase.getInstance()
                                        .getReference("user")
                                        .child("allusers")
                                        .child(list.get(tag).getUser().getUsername());

                                Task<Void> voidTask = child.removeValue();
                                voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "User Account Deleted",
                                                Toast.LENGTH_SHORT).show();
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
                alert.setTitle("Delete Account");
                alert.show();
            }
        };
        return new ViewAllAdminViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewAllAdminViewHolder viewHolder = (ViewAllAdminViewHolder) holder;
        UserInfo info = list.get(position);
        viewHolder.setUserinfo(info);
        viewHolder.setAdminname(info.getUser().getFirstname() + " " + info.getUser().getLastname());
        viewHolder.setAdminrole(info.getUser().getRole());
        viewHolder.setProfilepic(info.getUri());
        viewHolder.setTag(position);
        viewHolder.setOnClickDetail(this);
        viewHolder.setOnClickDelete(clickListener);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
