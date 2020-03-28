package com.example.friendies.books;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.friendies.R;
import com.example.friendies.adapter.BooksAdapter;
import com.example.friendies.adapter.MostDownloadsItemAdapter;
import com.example.friendies.adapter.OtherItemAdapter;
import com.example.friendies.adapter.PopularItemAdapter;
import com.example.friendies.adapter.RecentlyAddedItemAdapter;
import com.example.friendies.model.BooksModel;
import com.example.friendies.model.MostDownloadsItemModel;
import com.example.friendies.model.OtherItemModel;
import com.example.friendies.model.PopularItemModel;
import com.example.friendies.model.RecentlyAddedItemModel;
import com.example.friendies.ui.home.HomeFragment;
import com.example.friendies.ui.home.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {

    RecyclerView recyclerviewBooks;
    ArrayList<BooksModel> listBooks;
    BooksAdapter booksAdapter;
    ImageView imgBack;
    ImageView imgSearch;
    Button btnCancel;

    RelativeLayout layout_searchview;

    TextView txtCategory;

    private RequestQueue requestQueue;
    private final String JSON_URL = "http://192.168.43.56:8000/api/book/read";
    private final String IMG_URL = "http://192.168.43.56:8000/images/";
    private JsonArrayRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);
        getSupportActionBar().hide();

        imgBack = findViewById(R.id.imgBack);
        imgSearch = findViewById(R.id.imgSearch);
        btnCancel = findViewById(R.id.btnCancel);
        layout_searchview = findViewById(R.id.layout_searchview);

        txtCategory = findViewById(R.id.txtCategory);

        recyclerviewBooks = findViewById(R.id.books_recyclerview);
        listBooks = new ArrayList<>();

        Intent intent = getIntent();
        String popular_toptext = intent.getStringExtra("POPULAR_TOPTEXT");
        if(popular_toptext != null) {
            txtCategory.setText(popular_toptext);
        }
        String recently_added_toptext = intent.getStringExtra("RECENTLY_ADDED_TOPTEXT");
        if(recently_added_toptext != null) {
            txtCategory.setText(recently_added_toptext);
        }
        String most_downloads_toptext = intent.getStringExtra("MOST_DOWNLOADS_TOPTEXT");
        if(most_downloads_toptext != null) {
            txtCategory.setText(most_downloads_toptext);
        }
        String other_toptext = intent.getStringExtra("OTHER_TOPTEXT");
        if(other_toptext != null) {
            txtCategory.setText(other_toptext);
        }
        String category_toptext = intent.getStringExtra("CATEGORY_TOPTEXT");
        if(category_toptext != null) {
            txtCategory.setText(category_toptext);
        }

        requestQueue = Volley.newRequestQueue(BooksActivity.this);

        jsonRequest();

        //Book Onclick
        recyclerviewBooks.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerviewBooks,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(BooksActivity.this, BookDetailsActivity.class);
                        intent.putExtra("BOOK_COVER", listBooks.get(position).getCover());
                        intent.putExtra("BOOK_TITLE", listBooks.get(position).getTitle());
                        intent.putExtra("BOOK_AUTHOR", listBooks.get(position).getAuthor());
                        intent.putExtra("BOOK_DESCRIPTION", listBooks.get(position).getDescription());
                        intent.putExtra("BOOK_IMAGE", listBooks.get(position).getImgCover());
                        intent.putExtra("BOOK_CATEGORY_ID", listBooks.get(position).getCategory_id());
                        intent.putExtra("BOOK_NOD", listBooks.get(position).getNod());
                        intent.putExtra("BOOK_RATING", listBooks.get(position).getRating());
                        intent.putExtra("BOOK_PDF", listBooks.get(position).getPdf());
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

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_searchview.setVisibility(layout_searchview.VISIBLE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_searchview.setVisibility(layout_searchview.INVISIBLE);
            }
        });

    }

    private void jsonRequest() {
        request = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                        // Recently Added Item
                        BooksModel model = new BooksModel();
                        model.setId(jsonObject.getInt("id"));
                        model.setImgCover(IMG_URL + jsonObject.getString("image"));
                        model.setCover(IMG_URL + jsonObject.getString("cover"));
                        model.setTitle(jsonObject.getString("title"));
                        model.setAuthor(jsonObject.getString("author"));
                        model.setDescription(jsonObject.getString("description"));
                        model.setCategory_id(jsonObject.getInt("category_id"));
                        model.setNod(jsonObject.getInt("nod"));
                        //model.setRating(jsonObject.getInt("rating"));
                        model.setPdf(jsonObject.getString("pdf"));
                        listBooks.add(model);

                        //Log
                        Log.d("JSONObject", "onResponse: " +
                                jsonObject.getInt("id") + " " +
                                jsonObject.getString("title") + "   " +
                                jsonObject.getString("author")
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Recently Added Item
                recyclerviewBooks.setLayoutManager(new GridLayoutManager(BooksActivity.this,3));
                booksAdapter = new BooksAdapter(BooksActivity.this,listBooks);
                recyclerviewBooks.setAdapter(booksAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(request);
    }
}