package com.msdev.arduinotemp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Example : AppCompatActivity() {
    private lateinit var TextView:TextView
    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        supportActionBar?.title = ""
        supportActionBar?.hide()

        TextView = findViewById(R.id.link)

        TextView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/userdevil/IOT-ASSITANT"))
            startActivity(browserIntent)
        }

        // Initialize the webview
        val webView = findViewById<WebView>(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }
        }


        webView.loadData("<html><head><script src=\"https://gist.github.com/userdevil/1422dfec1fddf5df0dfb7105122e8841.js\"></script></head><body></body></html","text/html","utf-8")

        val web = findViewById<WebView>(R.id.web)
        web.settings.javaScriptEnabled = true
        web.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }
        }


        web.loadData("<html><head><script src=\"https://gist.github.com/userdevil/b09bf09af21dc69643bd51fa24c15431.js\"></script></head><body></body></html","text/html","utf-8")

        val webV = findViewById<WebView>(R.id.webV)
        webV.settings.javaScriptEnabled = true
        webV.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }
        }


        webV.loadData("<html><head><script src=\"https://gist.github.com/userdevil/ec34845dfc972a912c953a6821645272.js\"></script></head><body></body></html","text/html","utf-8")

        val webVi = findViewById<WebView>(R.id.webVi)
        webVi.settings.javaScriptEnabled = true
        webVi.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }
        }


        webVi.loadData("<html><head><script src=\"https://gist.github.com/userdevil/84d253ea7d2e25bbec6666b6dcfc0f32.js\"></script></head><body></body></html","text/html","utf-8")

        val webv = findViewById<WebView>(R.id.webv)
        webv.settings.javaScriptEnabled = true
        webv.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }
        }


        webv.loadData("<html><head><script src=\"https://gist.github.com/userdevil/958c0cd22fb82c29b0a460c74b27815c.js\"></script></head><body></body></html","text/html","utf-8")
    }

}