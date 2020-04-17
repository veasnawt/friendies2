package com.example.friendies.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;

public class DownloadsHolder extends RecyclerView.ViewHolder {
    public TextView txtTitle, txtCategory, txtDownloadStatus;
    public ImageView image;

    public DownloadsHolder(@NonNull View itemView) {
        super(itemView);
        image=itemView.findViewById(R.id.img_Cover_download);
        txtTitle=itemView.findViewById(R.id.tv_title);
        txtCategory=itemView.findViewById(R.id.tv_category);
        txtDownloadStatus=itemView.findViewById(R.id.tv_download);
    }
}
