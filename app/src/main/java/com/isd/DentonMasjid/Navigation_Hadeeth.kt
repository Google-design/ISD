package com.isd.DentonMasjid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient

class Navigation_Hadeeth : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_navigation__hadeeth, container, false)

        webView = view.findViewById(R.id.webview)
        setupWebView()

        return view
    }

    private fun setupWebView() {
        webView.settings.javaScriptEnabled = true // Enable JavaScript if needed
        webView.webViewClient = WebViewClient() // To open links in the WebView itself

        // Modify the User-Agent string to emulate a desktop browser
        val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
        webView.settings.userAgentString = userAgent

        // Load the URL
        webView.loadUrl("https://sunnah.com/bukhari")
    }

    // Handle back button press to navigate back in WebView
    fun onBackPressed(): Boolean {
        if (webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return false
    }

    
}