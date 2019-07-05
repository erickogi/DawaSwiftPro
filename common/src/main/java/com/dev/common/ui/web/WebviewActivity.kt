package com.dev.common.ui.web

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.R
import com.dev.common.utils.viewUtils.ViewUtils
import kotlinx.android.synthetic.main.toolback_bar.*
import kotlinx.android.synthetic.main.webview_activity.*

class WebviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity)
        ViewUtils().makeFullScreen(this)

        linear_back.setOnClickListener {
            onBackPressed()
        }


        val progressBar = findViewById<ProgressBar>(R.id.prg)
        val url: String = intent.getStringExtra("url") ?: ""


        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                view.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                view.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }

        }
        webView.settings.pluginState = WebSettings.PluginState.ON
        webView.settings.pluginState = WebSettings.PluginState.ON_DEMAND

        webView.settings.javaScriptEnabled = true

        webView.settings.setAppCacheEnabled(false)
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        val settings = webView.settings
        settings.domStorageEnabled = true

        webView.clearCache(true)
        webView.loadUrl(url)
    }

}
