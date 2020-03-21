package com.example.friendies.books;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.adapter.BooksAdapter;
import com.example.friendies.adapter.UserRatingsAdapter;
import com.example.friendies.model.UserRatingsModel;

import java.util.ArrayList;

public class BookDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerviewUserRatings;
    ArrayList<UserRatingsModel> listUserRatingsModel;
    UserRatingsAdapter userRatingsAdapter;

    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);
        getSupportActionBar().hide();

        recyclerviewUserRatings = findViewById(R.id.recylerview_user_ratings);

        imgBack = findViewById(R.id.imgBack);

        listUserRatingsModel = new ArrayList<>();

        for(int i = 3; i < 7; i++){
            UserRatingsModel model = new UserRatingsModel();
            model.setId(i);
            model.setUserpic(R.drawable.userpic);
            model.setUsername("anime_lover");
            model.setUser_rating("4 Stars");
            model.setUser_opinion("Opinion: I love this book so much, it is so fun to read, everyone should read this book. I can't wait to see more books like this!");
            listUserRatingsModel.add(model);
        }

        recyclerviewUserRatings.setLayoutManager(new GridLayoutManager(this,1));
        userRatingsAdapter = new UserRatingsAdapter(this,listUserRatingsModel);
        recyclerviewUserRatings.setAdapter(userRatingsAdapter);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookDetailsActivity.this, BooksActivity.class));
            }
        });

    }
}
