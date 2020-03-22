package com.example.friendies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.holder.UserRatingsHolder;
import com.example.friendies.model.UserRatingsModel;

import java.util.ArrayList;

public class UserRatingsAdapter extends RecyclerView.Adapter<UserRatingsHolder> {

    Context context;
    ArrayList<UserRatingsModel> listUserRatings;

    public UserRatingsAdapter(Context context, ArrayList<UserRatingsModel> listUserRatings) {
        this.context = context;
        this.listUserRatings = listUserRatings;
    }

    @NonNull
    @Override
    public UserRatingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_ratings,null);

        return new UserRatingsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRatingsHolder holder, int position) {
        holder.userpic.setImageResource(listUserRatings.get(position).getUserpic());
        holder.username.setText(listUserRatings.get(position).getUsername());
        holder.user_rating.setText(listUserRatings.get(position).getUser_rating());
        holder.user_opinion.setText(listUserRatings.get(position).getUser_opinion());
    }

    @Override
    public int getItemCount() {
        return listUserRatings.size();
    }
}
