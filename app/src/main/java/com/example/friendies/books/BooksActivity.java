package com.example.friendies.books;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.adapter.BooksAdapter;
import com.example.friendies.model.BooksModel;

import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {

    RecyclerView recyclerviewBooks;
    ArrayList<BooksModel> listBooks;
    BooksAdapter booksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);

        recyclerviewBooks = findViewById(R.id.books_recyclerview);
        listBooks = new ArrayList<>();

        for (int i = 0; i < 10;i++) {
            BooksModel model = new BooksModel();
            model.setTitle("Solo Leveling");
            model.setAuthor("Gimee Lee");
            model.setImgCover(R.drawable.sololevel);
            listBooks.add(model);
        }

        recyclerviewBooks.setLayoutManager(new GridLayoutManager(this,3));
        booksAdapter = new BooksAdapter(this,listBooks);
        recyclerviewBooks.setAdapter(booksAdapter);

    }
}