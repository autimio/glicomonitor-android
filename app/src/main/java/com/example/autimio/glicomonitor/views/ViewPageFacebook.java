package com.example.autimio.glicomonitor.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.autimio.glicomonitor.R;

/**
 * Created by Autímio M. B. Filho on 19/12/2017.
 */

public class ViewPageFacebook extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_glicose);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://www.google.com");
    }
}
