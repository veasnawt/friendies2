package com.example.friendies.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;

public class UserRatingsHolder extends RecyclerView.ViewHolder {

    public ImageView userpic;
    public TextView user_rating, user_opinion, username;

    public UserRatingsHolder(@NonNull View itemView) {
        super(itemView);
        userpic = itemView.findViewById(R.id.img_userpic);
        username = itemView.findViewById(R.id.txtUsername);
        user_rating = itemView.findViewById(R.id.txtUserRating);
        user_opinion = itemView.findViewById(R.id.txtUserOpinion);
    }
}
