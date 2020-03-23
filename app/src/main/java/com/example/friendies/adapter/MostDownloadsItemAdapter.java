package com.example.friendies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.holder.MostDownloadsItemHolder;
import com.example.friendies.model.MostDownloadsItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MostDownloadsItemAdapter extends RecyclerView.Adapter<MostDownloadsItemHolder> {
    Context context;
    ArrayList<MostDownloadsItemModel> listMostDownloadsItemModel;

    public MostDownloadsItemAdapter(Context context, ArrayList<MostDownloadsItemModel> listMostDownloadsItemModel) {
        this.context = context;
        this.listMostDownloadsItemModel = listMostDownloadsItemModel;
    }

    @NonNull
    @Override
    public MostDownloadsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_most_downloads,null);
        return new MostDownloadsItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MostDownloadsItemHolder holder, int position) {
        Picasso.get().load(listMostDownloadsItemModel.get(position).getImgCover()).into(holder.imgCover);
        holder.txtTitle.setText(listMostDownloadsItemModel.get(position).getTitle());
        holder.txtAuthor.setText(listMostDownloadsItemModel.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return listMostDownloadsItemModel.size();
    }
}
