package com.example.friendies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.holder.LibraryHolder;
import com.example.friendies.model.LibraryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryHolder> {

    Context context;
    ArrayList<LibraryModel> list;

    public LibraryAdapter(Context context, ArrayList<LibraryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LibraryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books,null);
        return new LibraryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryHolder holder, int position) {
        holder.tv_titleBook.setText(list.get(position).getBookTitle());
        Picasso.get().load(list.get(position).getURL_BookImage()).into(holder.imageViewBook);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
