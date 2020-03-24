package com.example.friendies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.holder.PopularItemholder;
import com.example.friendies.model.PopularItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopularItemAdapter extends RecyclerView.Adapter<PopularItemholder> {
    Context context;
    ArrayList<PopularItemModel> listPopularModel;

    public PopularItemAdapter(Context context, ArrayList<PopularItemModel> listPopularModel) {
        this.context = context;
        this.listPopularModel = listPopularModel;
    }

    @NonNull
    @Override
    public PopularItemholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular,null);
        return new PopularItemholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularItemholder holder, int position) {
//        holder.imgCover.setImageResource(listPopularModel.get(position).getImgCover());
         Picasso.get().load(listPopularModel.get(position).getCover()).into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return listPopularModel.size();
    }
}
