package com.example.friendies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.holder.CategoryHolder;
import com.example.friendies.model.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

    Context context;
    ArrayList<CategoryModel> categoryList;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_item_category,null);

        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.textViewTitle.setText(categoryList.get(position).getTitle());
        Picasso.get().load(categoryList.get(position).getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
