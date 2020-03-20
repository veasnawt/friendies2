package com.example.friendies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.holder.BooksHolder;
import com.example.friendies.model.BooksModel;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksHolder> {

    Context context;
    ArrayList<BooksModel> listBooks;

    public BooksAdapter(Context context, ArrayList<BooksModel> listBooks) {
        this.context = context;
        this.listBooks = listBooks;
    }

    @NonNull
    @Override
    public BooksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books,null);

        return new BooksHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksHolder holder, int position) {
        holder.imgCover.setImageResource(R.drawable.sololevel);
        holder.txtTitle.setText(listBooks.get(position).getTitle());
        holder.txtAuthor.setText(listBooks.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }
}

