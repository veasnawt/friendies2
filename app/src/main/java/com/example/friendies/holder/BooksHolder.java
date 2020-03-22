package com.example.friendies.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;

public class BooksHolder extends RecyclerView.ViewHolder {

    public ImageView imgCover;
    public TextView txtTitle, txtAuthor;

    public BooksHolder(@NonNull View itemView) {
        super(itemView);
        imgCover = itemView.findViewById(R.id.books_imgCover);
        txtTitle = itemView.findViewById(R.id.books_txtTitle);
        txtAuthor = itemView.findViewById(R.id.books_txtAuthor);
    }
}