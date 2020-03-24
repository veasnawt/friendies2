package com.example.friendies.books;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.adapter.BooksAdapter;
import com.example.friendies.adapter.UserRatingsAdapter;
import com.example.friendies.model.UserRatingsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerviewUserRatings;
    ArrayList<UserRatingsModel> listUserRatingsModel;
    UserRatingsAdapter userRatingsAdapter;

    ImageView imgBack;
    Button btnRead;

    ImageView img_book_cover, imgCover;
    TextView title, author, description, category, nod, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);
        getSupportActionBar().hide();

        recyclerviewUserRatings = findViewById(R.id.recylerview_user_ratings);

        imgBack = findViewById(R.id.imgBack);
        btnRead = findViewById(R.id.btnRead);

        img_book_cover = findViewById(R.id.img_book_cover);
        imgCover = findViewById(R.id.books_imgCover);
        title = findViewById(R.id.txtTitle);
        author = findViewById(R.id.txtAuthor);
        description = findViewById(R.id.txtDescription);
        category = findViewById(R.id.books_txtCategory);
        nod = findViewById(R.id.books_txtDownloads);
        rating = findViewById(R.id.books_txtRatings);

        Intent intent = getIntent();
        String book_cover = intent.getStringExtra("BOOK_COVER");
        String book_image = intent.getStringExtra("BOOK_IMAGE");
        String book_title = intent.getStringExtra("BOOK_TITLE");
        String book_author = intent.getStringExtra("BOOK_AUTHOR");
        String book_description = intent.getStringExtra("BOOK_DESCRIPTION");
//        int category_id = intent.getIntExtra("BOOK_CATEGORY_ID");
//        int nod = intent.getIntExtra("BOOK_NOD");
//        int rating = intent.getIntExtra("BOOK_RATING");

        Picasso.get().load(book_cover).into(img_book_cover);
        Picasso.get().load(book_image).into(imgCover);
        title.setText(book_title);
        author.setText(book_author);
        description.setText(book_description);

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

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookDetailsActivity.this, PDFView.class));
            }
        });

    }
}
