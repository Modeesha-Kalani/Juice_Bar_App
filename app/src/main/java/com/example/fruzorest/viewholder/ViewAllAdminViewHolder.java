package com.example.fruzorest.viewholder;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fruzorest.R;
import com.example.fruzorest.model.UserInfo;

public class ViewAllAdminViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout root;
    public ImageView profilepic;
    public TextView adminname, adminrole;
    public Button viewbutton, deletebutton;
    private UserInfo userinfo;

    public ViewAllAdminViewHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.ar_linearlayout);
        profilepic = itemView.findViewById(R.id.ar_image);
        adminname = itemView.findViewById(R.id.ar_name);
        adminrole = itemView.findViewById(R.id.ar_role);
        viewbutton = itemView.findViewById(R.id.ar_viewbtn);
        deletebutton = itemView.findViewById(R.id.ar_deletebtn);

    }

    public void setProfilepic(Uri uri) {
        profilepic.setImageURI(null);
        Glide.with(userinfo.getContext()).load(uri).apply(RequestOptions.circleCropTransform()).into(profilepic);
    }

    public void setAdminname(String adminname) {
        this.adminname.setText(adminname);
    }

    public void setAdminrole(String adminrole) {
        this.adminrole.setText(adminrole);
    }

    public void setTag(int i) {
        root.setTag(i);
        viewbutton.setTag(i);
        deletebutton.setTag(i);
    }

    public int getTag() {
        return (int) root.getTag();
    }

    public void setUserinfo(UserInfo info) {
        this.userinfo = info;
    }

    public void setOnClickDetail(View.OnClickListener listener){
        viewbutton.setOnClickListener(listener);
    }

    public void setOnClickDelete(View.OnClickListener listener){
        deletebutton.setOnClickListener(listener);
    }
}
