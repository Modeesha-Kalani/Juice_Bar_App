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

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout root;
    public ImageView image;
    public TextView pname, price;
    public Context context;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.pre_linear);
        image = itemView.findViewById(R.id.pre_image);
        pname = itemView.findViewById(R.id.pre_name);
        price = itemView.findViewById(R.id.pre_price);
    }

    public void setImage(Uri uri) {
        image.setImageURI(null);
        Glide.with(context).load(uri).into(image);
    }

    public void setName(String name) {
        this.pname.setText(name);
    }

    public void setPrice(String price) {
        this.price.setText(price);
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


}
