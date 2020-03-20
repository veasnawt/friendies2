package com.example.friendies.books;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.adapter.BooksAdapter;
import com.example.friendies.model.BooksModel;
import com.example.friendies.ui.home.HomeFragment;
import com.example.friendies.ui.home.RecyclerItemClickListener;

import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {

    RecyclerView recyclerviewBooks;
    ArrayList<BooksModel> listBooks;
    BooksAdapter booksAdapter;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);
        getSupportActionBar().hide();

        imgBack = findViewById(R.id.imgBack);

        recyclerviewBooks = findViewById(R.id.books_recyclerview);
        listBooks = new ArrayList<>();

        for (int i = 0; i < 20;i++) {
            BooksModel model = new BooksModel();
            model.setTitle("Solo Leveling");
            model.setAuthor("Gimee Lee");
            model.setImgCover(R.drawable.sololevel);
            listBooks.add(model);
        }

        recyclerviewBooks.setLayoutManager(new GridLayoutManager(this,3));
        booksAdapter = new BooksAdapter(this,listBooks);
        recyclerviewBooks.setAdapter(booksAdapter);

        //Book Onclick
        recyclerviewBooks.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerviewBooks ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(BooksActivity.this, BooksActivity.class);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BooksActivity.this, HomeFragment.class);
                startActivity(intent);
            }
        });

    }
}