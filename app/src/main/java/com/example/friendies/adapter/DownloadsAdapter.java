package com.example.friendies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.holder.DownloadsHolder;
import com.example.friendies.model.DownloadsModel;
import com.example.friendies.ui.download.DownloadFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsHolder> {

    Context context;
    ArrayList<DownloadsModel> DownloadsList;

    public DownloadsAdapter(Context context, ArrayList<DownloadsModel> DownloadsList) {
        this.context = context;
        this.DownloadsList = DownloadsList;
    }

    @NonNull
    @Override
    public DownloadsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download,null);

        return new DownloadsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadsHolder holder, int position) {
        holder.txtTitle.setText(DownloadsList.get(position).getTitle());
        holder.txtCategory.setText(DownloadsList.get(position).getCategory());
        holder.txtDownloadStatus.setText(DownloadsList.get(position).getDownloadStatus());
        Picasso.get().load(DownloadsList.get(position).getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return DownloadsList.size();
    }
}
