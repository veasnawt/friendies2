package com.example.friendies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.holder.RecentlyAddedItemHolder;
import com.example.friendies.model.RecentlyAddedItemModel;

import java.util.ArrayList;

public class RecentlyAddedItemAdapter extends RecyclerView.Adapter<RecentlyAddedItemHolder> {
    Context context;
    ArrayList<RecentlyAddedItemModel> listRecentlyAddedItemModel;

    public RecentlyAddedItemAdapter(Context context, ArrayList<RecentlyAddedItemModel> listRecentlyAddedItemModel) {
        this.context = context;
        this.listRecentlyAddedItemModel = listRecentlyAddedItemModel;
    }

    @NonNull
    @Override
    public RecentlyAddedItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recently_added,null);
        return new RecentlyAddedItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyAddedItemHolder holder, int position) {
        holder.imgCover.setImageResource(listRecentlyAddedItemModel.get(position).getImgCover());
        holder.txtTitle.setText(listRecentlyAddedItemModel.get(position).getTitle());
        holder.txtAuthor.setText(listRecentlyAddedItemModel.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return listRecentlyAddedItemModel.size();
    }
}
