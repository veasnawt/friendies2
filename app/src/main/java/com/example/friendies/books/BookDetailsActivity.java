package com.example.friendies.books;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
    Button btnRead, btnDownload;

    ImageView img_book_cover, imgCover;
    TextView title, author, description, category, nod, rating;

    private static final int WRITE_PERMISSION = 1001;
    private final String PDF_URL = "http://192.168.43.56:8000/pdf/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);
        getSupportActionBar().hide();

        recyclerviewUserRatings = findViewById(R.id.recylerview_user_ratings);

        imgBack = findViewById(R.id.imgBack);
        btnRead = findViewById(R.id.btnRead);
        btnDownload = findViewById(R.id.btnDownload);

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
        final String book_pdf = intent.getStringExtra("BOOK_PDF");

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
                Intent intent = new Intent(BookDetailsActivity.this, PDFView.class);
                intent.putExtra("BOOK_PDF", book_pdf);
                startActivity(intent);
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(ContextCompat.checkSelfPermission(BookDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        String fileName = book_pdf;
                        downloadFile(fileName, PDF_URL + book_pdf);
                    }else {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);

                    }
                }
            }
        });

    }

    private void downloadFile(String fileName, String url) {
        Intent intent = getIntent();
        String book_pdf = intent.getStringExtra("BOOK_PDF");
        Uri downloadUri = Uri.parse(PDF_URL + book_pdf);
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        try {
            if(downloadManager != null) {
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setTitle(fileName)
                        .setDescription("Downloading " + fileName)
                        .setAllowedOverMetered(true)
                        .setAllowedOverRoaming(true)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                        .setMimeType(getMimeType(downloadUri));
                downloadManager.enqueue(request);
                Toast.makeText(this, "Downloading " + book_pdf + "...", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, downloadUri);
                startActivity(intent1);
            }

        }catch (Exception e) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            Log.e("Error:MAIN", "E: " + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == WRITE_PERMISSION) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = getIntent();
                String book_pdf = intent.getStringExtra("BOOK_PDF");
                String fileName = book_pdf;
                downloadFile(fileName, PDF_URL + book_pdf);
            }else {
                Toast.makeText(this, "Download Failed!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private String getMimeType(Uri uri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));
    }
}
