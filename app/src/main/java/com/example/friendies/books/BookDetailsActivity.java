package com.example.friendies.books;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.adapter.UserRatingsAdapter;
import com.example.friendies.model.DownloadsModel;
import com.example.friendies.model.UserRatingsModel;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class BookDetailsActivity extends AppCompatActivity {


    static BookDetailsActivity INSTANCE;

    RecyclerView recyclerviewUserRatings;
    ArrayList<UserRatingsModel> listUserRatingsModel;
    UserRatingsAdapter userRatingsAdapter;

    ArrayList<DownloadsModel> listDownloadsModel;

    ImageView imgBack;
    Button btnRead, btnDownload;

    ImageView img_book_cover, imgCover;
    TextView title, author, description, category, nod, rating;

    private static final int WRITE_PERMISSION = 1001;
    private final String PDF_URL = "http://192.168.43.56:8000/pdf/";

    int timesDownloaded = 0;

    String reqDownload = "no_req_download";

    DownloadsModel model = new DownloadsModel();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        INSTANCE = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);
        getSupportActionBar().hide();

        doStuffs();
        readJson();
    }


    public void doStuffs() {

        recyclerviewUserRatings = findViewById(R.id.recylerview_user_ratings);

        listDownloadsModel = new ArrayList<>();

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
        final String book_cover = intent.getStringExtra("BOOK_COVER");
        final String book_image = intent.getStringExtra("BOOK_IMAGE");
        final String book_title = intent.getStringExtra("BOOK_TITLE");
        final String book_author = intent.getStringExtra("BOOK_AUTHOR");
        final String book_description = intent.getStringExtra("BOOK_DESCRIPTION");
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

//                        Intent intent = new Intent(BookDetailsActivity.this, DownloadFragment.class);
//                        intent.putExtra("BOOK_TITLE", book_title);
//                        intent.putExtra("BOOK_IMAGE", book_image);
//                        intent.putExtra("BOOK_COVER", book_cover);
//                        intent.putExtra("BOOK_AUTHOR", book_author);
//                        intent.putExtra("BOOK_DESCRIPTOIN", book_description);
//                        intent.putExtra("BOOK_PDF", book_pdf);
//                        intent.putExtra("MSG", "req_download");
//                        startActivity(intent);
                            DownloadsModel model = new DownloadsModel();
                            model.setImage(book_image);
                            model.setTitle(book_title);
                            model.setCategory("Fantasy");
                            model.setDownloadStatus("Downloaded");

                            String filename = "downloaded_books.json";
                            Gson gson = new Gson();
                            String json = gson.toJson(model);

                            File file = new File(BookDetailsActivity.this.getFilesDir(), filename);

                            try {
                                file.createNewFile(); // if file already exists will do nothing
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //START FIS
                            try {
                                FileInputStream fis = BookDetailsActivity.this.openFileInput(filename);
                                InputStreamReader inputStreamReader =
                                        new InputStreamReader(fis, StandardCharsets.UTF_8);
                                StringBuilder stringBuilder = new StringBuilder();
                                try(BufferedReader reader = new BufferedReader(inputStreamReader)) {
                                    String line = reader.readLine();
                                    while (line != null) {
                                        stringBuilder.append(line).append('\n');
                                        line = reader.readLine();
                                    }
                                    if(stringBuilder.length() != 0 ) {

                                        if (stringBuilder.length() > 0) {
                                            int last, prev = stringBuilder.length() - 1;
                                            while ((last = stringBuilder.lastIndexOf("\n", prev)) == prev) { prev = last - 1; }
                                            if (last >= 0) { stringBuilder.delete(last, stringBuilder.length()); }
                                        }

                                        String data = stringBuilder + ",\n" + json + "\n]";

                                        //START FOS

                                        try (FileOutputStream fos = BookDetailsActivity.this.openFileOutput(filename, Context.MODE_PRIVATE)) {
                                            fos.write(data.getBytes());
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                        //END FOS
                                    } else {
                                        //START FOS

                                        try (FileOutputStream fos = BookDetailsActivity.this.openFileOutput(filename, Context.MODE_PRIVATE)) {
                                            fos.write(("[\n" + json + "\n]" ).getBytes());
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                        //END FOS
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            //END FIS

                        reqDownload = "req_download";


                    }else {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);

                    }
                }
            }
        });
    }

    public void readJson() {
        Gson gson = new Gson();

        // JSON file to Java object
        try {
            Object object = gson.fromJson(new FileReader("/data/data/com.example.friendies/files/downloaded_books.json"), Object.class);

            String jsonString = object.toString();

            Log.d("READ_JSON", "readJson: " + jsonString);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    public static BookDetailsActivity getActivityInstance()
    {
        return INSTANCE;
    }

    public ArrayList<DownloadsModel> getData()
    {
        return this.listDownloadsModel;
    }

    public String getReqDownload() {
        return this.reqDownload;
    }
}
