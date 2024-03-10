package com.isd.isd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.facebook.shimmer.ShimmerFrameLayout


class Navigation_Calendar : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation__calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shimmerFrameLayout = view.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container3)
        val webView = view.findViewById<WebView>(R.id.webview_calendar)
        webView.settings.javaScriptEnabled = true
        webView.setWebViewClient(WebViewClient())
        webView.loadUrl("https://calendar.google.com/calendar/embed?src=isd1105%40gmail.com&ctz=America%2FChicago")
        shimmerFrameLayout.stopShimmer()
        shimmerFrameLayout.hideShimmer()
    }


}