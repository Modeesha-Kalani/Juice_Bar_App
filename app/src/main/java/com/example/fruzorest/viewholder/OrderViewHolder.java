package com.example.fruzorest.viewholder;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fruzorest.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout root;
    public TextView regdetail, largdetail, total, pname;
    public Context context;


    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.orderlinearlayout);
        regdetail = itemView.findViewById(R.id.or_regdetail);
        largdetail = itemView.findViewById(R.id.or_largedetail);
        total = itemView.findViewById(R.id.or_total);
        pname = itemView.findViewById(R.id.or_productname);
    }

    public void setRegdetail(String regdetail) {
        this.regdetail.setText(regdetail);
    }

    public void setLargdetail(String largdetail) {
        this.largdetail.setText(largdetail);
    }

    public void setTotal(String total) {
        this.total.setText(total);
    }

    public void setTag(int i) {
        root.setTag(i);
    }

    public int getTag() {
        return (int) root.getTag();
    }

    public void setContext(Context info) {
        this.context = info;
    }

    public void setpName(String name) {
        pname.setText(name);
    }


}
