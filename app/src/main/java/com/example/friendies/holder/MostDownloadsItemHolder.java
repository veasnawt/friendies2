package com.example.friendies.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;

public class MostDownloadsItemHolder extends RecyclerView.ViewHolder {

    public ImageView imgCover;
    public TextView txtTitle, txtAuthor;

    public MostDownloadsItemHolder(@NonNull View itemView) {
        super(itemView);
        imgCover = itemView.findViewById(R.id.most_downloads_book_cover);
        txtTitle = itemView.findViewById(R.id.text_most_downloads_book_title);
        txtAuthor = itemView.findViewById(R.id.most_downloads_book_author);
    }
}
