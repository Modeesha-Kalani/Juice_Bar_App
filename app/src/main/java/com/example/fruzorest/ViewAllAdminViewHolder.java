package com.example.fruzorest;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewAllAdminViewHolder  extends RecyclerView.ViewHolder {
    public LinearLayout root;
    public ImageView profilepic;
    public TextView adminname,adminrole;
    public Button viewbutton,deletebutton;

    public ViewAllAdminViewHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.ar_linearlayout);
        profilepic = itemView.findViewById(R.id.ar_image);
        adminname = itemView.findViewById(R.id.ar_name);
        adminrole = itemView.findViewById(R.id.ar_role);
        viewbutton =itemView.findViewById(R.id.ar_viewbtn);
        deletebutton = itemView.findViewById(R.id.ar_deletebtn);


    }

    public void setProfilepic(ImageView profilepic) {
        this.profilepic = profilepic;
    }

    public void setAdminname(String adminname) {
        this.adminname.setText(adminname);
    }

    public void setAdminrole(String adminrole) {
        this.adminrole.setText(adminrole);
    }

    public void setTag(int i) {
        root.setTag(i);
    }

    public int getTag() {
        return (int) root.getTag();
    }
}
