package com.example.mycasino3.view.activity

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import com.example.mycasino3.R

class WebViewActivity : AppCompatActivity() {

    private var webView : WebView? = null
    private var fileUploadCallback: ValueCallback<Array<Uri>>? = null
    private val FILE_CHOOSER_RESULT_CODE = 1
    private var customView: View? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView = findViewById(R.id.id_webview)

        setupWebView()

        if (savedInstanceState != null) {
            webView?.restoreState(savedInstanceState)
        } else {
            webView?.loadUrl(intent.getStringExtra("url")!!)
        }

    }

    private fun setupWebView() {
        val webSettings: WebSettings? = webView?.settings

        webSettings?.javaScriptEnabled = true
        webSettings?.domStorageEnabled = true
        webSettings?.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings?.databaseEnabled = true
        webSettings?.databasePath = applicationContext.getDir("webview_databases", 0).path
        webSettings?.allowFileAccess = true
        webSettings?.mediaPlaybackRequiresUserGesture = false
        webSettings?.loadsImagesAutomatically = true
        webSettings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        webView?.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

        }

        webView?.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                if (fileUploadCallback != null) {
                    fileUploadCallback!!.onReceiveValue(null)
                    fileUploadCallback = null
                }

                fileUploadCallback = filePathCallback

                val intent = fileChooserParams.createIntent()
                try {
                    @Suppress("DEPRECATION")
                    startActivityForResult(intent, FILE_CHOOSER_RESULT_CODE)
                } catch (e: Exception) {
                    fileUploadCallback = null
                    return false
                }

                return true
            }
            override fun onShowCustomView(view: View, callback: CustomViewCallback) {
                super.onShowCustomView(view, callback)

                if (customView != null) {
                    callback.onCustomViewHidden()
                    return
                }

                customView = view
                customView?.let {
                    val decorView = window.decorView as FrameLayout
                    decorView.addView(it, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                }

                webView?.visibility = View.GONE
            }

            override fun onHideCustomView() {
                super.onHideCustomView()

                customView?.let {
                    val decorView = window.decorView as FrameLayout
                    decorView.removeView(it)
                    customView = null
                }

                webView?.visibility = View.VISIBLE
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView?.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView?.restoreState(savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (webView?.canGoBack() == true)
            webView?.goBack()
        else finishAffinity()
    }

}