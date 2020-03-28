package com.example.friendies.books;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.friendies.R;

public class PDFView extends AppCompatActivity {

    private final String PDF_URL = "http://192.168.43.56:8000/pdf/";

    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view);
        getSupportActionBar().hide();

        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient());

        Intent intent = getIntent();
        String book_pdf = intent.getStringExtra("BOOK_PDF");
        String pdf_path = PDF_URL + book_pdf;

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() [ " +
                        "document.querySelector('[role=\"toolbar\"]').remove();])()");
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.loadUrl("https://docs.google.com/viewer?url=" + pdf_path);
    }

}
