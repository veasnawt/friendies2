package com.example.friendies.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;

public class LibraryHolder extends RecyclerView.ViewHolder {
    public TextView tv_titleBook;
    public ImageView imageViewBook;


    public LibraryHolder(@NonNull View itemView) {
        super(itemView);
        imageViewBook = itemView.findViewById(R.id.books_imgCover);
        tv_titleBook = itemView.findViewById((R.id.books_txtTitle));

    }
}
