package com.example.rajukumarsingh.bloodbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class About_Activity extends AppCompatActivity {
    WebView view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        view1 = (WebView) findViewById(R.id.webview2);
        view1.getSettings().setJavaScriptEnabled(true);
        view1.loadUrl("file:///android_asset/done.html");
    }
}
