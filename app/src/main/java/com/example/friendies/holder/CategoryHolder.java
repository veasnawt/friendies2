package com.example.friendies.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;

public class CategoryHolder extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public ImageView image;


    public CategoryHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle=itemView.findViewById(R.id.book_title_category);
        image=itemView.findViewById(R.id.image_category);
    }
}
