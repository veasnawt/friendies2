package com.example.friendies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.holder.OtherItemHolder;
import com.example.friendies.model.OtherItemModel;

import java.util.ArrayList;

public class OtherItemAdapter extends RecyclerView.Adapter<OtherItemHolder> {
    Context context;
    ArrayList<OtherItemModel> listOtherItemModel;

    public OtherItemAdapter(Context context, ArrayList<OtherItemModel> listOtherItemModel) {
        this.context = context;
        this.listOtherItemModel = listOtherItemModel;
    }

    @NonNull
    @Override
    public OtherItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other,null);
        return new OtherItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherItemHolder holder, int position) {
        holder.imgCover.setImageResource(listOtherItemModel.get(position).getImgCover());
        holder.txtTitle.setText(listOtherItemModel.get(position).getTitle());
        holder.txtAuthor.setText(listOtherItemModel.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return listOtherItemModel.size();
    }
}
