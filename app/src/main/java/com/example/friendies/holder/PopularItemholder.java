package com.example.friendies.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;

public class PopularItemholder extends RecyclerView.ViewHolder {
    public ImageView cover;

    public PopularItemholder(@NonNull View itemView) {
        super(itemView);
        cover = itemView.findViewById(R.id.imgCover);
    }
}
