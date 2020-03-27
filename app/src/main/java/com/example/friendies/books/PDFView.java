package com.example.friendies.books;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.friendies.R;
import com.github.barteksc.pdfviewer.source.AssetSource;

public class PDFView extends AppCompatActivity {

    com.github.barteksc.pdfviewer.PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view);
        getSupportActionBar().hide();

        pdfView = findViewById(R.id.pdfView);

        pdfView.fromAsset("veasnawt_cv.pdf")
                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .invalidPageColor(Color.WHITE) // color of page that is invalid and cannot be loaded
                .load();
    }
}
