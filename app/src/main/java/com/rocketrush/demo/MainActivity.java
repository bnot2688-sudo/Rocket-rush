package com.rocketrush.demo;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        String html =
                "<!DOCTYPE html>" +
                "<html><head>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0, user-scalable=no'>" +
                "<style>" +
                "html,body{margin:0;padding:0;width:100%;height:100%;overflow:hidden;background:#07111f;font-family:Arial;color:white}" +
                "#game{position:relative;width:100%;height:100%;background:linear-gradient(#071a35,#02060d);overflow:hidden}" +
                "#rocket{position:absolute;font-size:45px;left:50px;top:45
