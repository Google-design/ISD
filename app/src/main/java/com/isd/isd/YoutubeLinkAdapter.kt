package com.isd.isd

//class YoutubeLinkAdapter {
//     YoutubeLinkAdapter.kt
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.cardview.widget.CardView
//import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class YoutubeLinkAdapter(private val links: List<String>) :
    RecyclerView.Adapter<YoutubeLinkAdapter.LinkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_youtube_link, parent, false)
        return LinkViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        val link = links[position]

//        // Set WebView settings
//        holder.webView.settings.javaScriptEnabled = true
//        holder.webView.webChromeClient = WebChromeClient()
//        holder.webView.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                // Adjust WebView size after the page is loaded
//                view?.evaluateJavascript(
//                    "(function() { return document.body.scrollHeight; })();"
//                ) { contentHeight ->
//                    val height = contentHeight.toFloat()
//                    holder.webView.layoutParams.height = height.toInt()
//                    holder.webView.requestLayout()
//                }
//            }
//        }
//
//        // Load the YouTube embed link into WebView
//        holder.webView.loadData(link, "text/html", "utf-8")
    }

    override fun getItemCount(): Int {
        return links.size
    }

    inner class LinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val webView: WebView = itemView.findViewById(R.id.webView)
    }
}



//class YoutubeLinkAdapter(private val links: List<String>) :
//    RecyclerView.Adapter<YoutubeLinkAdapter.LinkViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_youtube_link, parent, false)
//        return LinkViewHolder(itemView)
//        }
//
//    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
//        val link = links[position]
//
//        // Get the width of the parent RecyclerView if available
//        var recyclerViewWidth = if (holder.itemView.parent is WebView) {
//            (holder.itemView.parent as WebView).width
//        } else {
//            // Handle the case when parent is not a RecyclerView
//            // For example, set a default width
//            // You can replace this with any suitable default value
//            holder.itemView.resources.displayMetrics.widthPixels
//        }
//
//        // Define aspect ratio
//        val aspectRatioWidth = 16
//        val aspectRatioHeight = 9
//
//        // Calculate WebView height based on aspect ratio
//        recyclerViewWidth = recyclerViewWidth/3
//        val webViewHeight = (recyclerViewWidth.toFloat() / aspectRatioWidth.toFloat() * aspectRatioHeight.toFloat()).toInt()
//
//        // Construct iframe HTML with updated width and height
//        val iframeHtml = link.replaceFirst("width=\"[0-9]+\"".toRegex(), "width=\"$recyclerViewWidth\"")
//            .replaceFirst("height=\"[0-9]+\"".toRegex(), "height=\"$webViewHeight\"")
//
//        // Load the modified HTML into WebView
//        holder.webView.loadDataWithBaseURL(null, iframeHtml, "text/html", "utf-8", null)
//
//        // Additional WebView settings
//        holder.webView.settings.javaScriptEnabled = true
//        holder.webView.webChromeClient = WebChromeClient()
//        holder.webView.webViewClient = WebViewClient()
//    }
//
//
////    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
////        val link = links[position]
////
////
//
////        // Add a ViewTreeObserver to get the width after the view has been laid out
////        holder.itemView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
////            override fun onGlobalLayout() {
////                // Remove the listener to avoid multiple calls
////                holder.itemView.viewTreeObserver.removeOnGlobalLayoutListener(this)
////
////                // Calculate WebView width
////                val webViewWidth = holder.itemView.width
////
////                // Define aspect ratio
////                val aspectRatioWidth = 1
////                val aspectRatioHeight = 1
////
////                // Calculate WebView height based on aspect ratio
////                val webViewHeight = (webViewWidth.toFloat() / aspectRatioWidth.toFloat() * aspectRatioHeight.toFloat()).toInt()
////
////                // Construct iframe HTML with updated width and height
////                val iframeHtml = link.replaceFirst("width=\"[0-9]+\"".toRegex(), "width=\"$webViewWidth\"")
////                    .replaceFirst("height=\"[0-9]+\"".toRegex(), "height=\"$webViewHeight\"")
////
////                // Load the modified HTML into WebView
////                holder.webView.loadDataWithBaseURL(null, iframeHtml, "text/html", "utf-8", null)
////
////                // Additional WebView settings
////                holder.webView.settings.javaScriptEnabled = true
////                holder.webView.webChromeClient = WebChromeClient()
////                holder.webView.webViewClient = WebViewClient()
////            }
////        })
////    }
//
//
//
////    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
////
//////        val link = links[position]
////////        holder.textViewLink.text = link
//////        holder.webView.loadData(link,"text/html","utf-8")
//////        holder.webView.settings.javaScriptEnabled = true
//////        holder.webView.webChromeClient = WebChromeClient()
//////        holder.webView.webViewClient = WebViewClient()
//////        holder.webView.loadUrl(link)
////
////        val link = links[position]
////
////        // Calculate WebView width
////        val webViewWidth = holder.webView.width
////
////        Log.d("iframe","webview width: ${webViewWidth}")
////
////
////
////
////        // Define aspect ratio
////        val aspectRatioWidth = 16
////        val aspectRatioHeight = 9
////
////        // Calculate WebView height based on aspect ratio
////        val webViewHeight = (webViewWidth.toFloat() / aspectRatioWidth.toFloat() * aspectRatioHeight.toFloat()).toInt()
////
////        // Construct iframe HTML with updated width and height
////        val iframeHtml = link.replaceFirst("width=\"[0-9]+\"".toRegex(), "width=\"$webViewWidth\"")
////            .replaceFirst("height=\"[0-9]+\"".toRegex(), "height=\"$webViewHeight\"")
////        Log.d("iframe","${iframeHtml}")
////
////
////        // Load the modified HTML into WebView
////        holder.webView.loadDataWithBaseURL(null, iframeHtml, "text/html", "utf-8", null)
////
////        // Additional WebView settings
////        holder.webView.settings.javaScriptEnabled = true
////        holder.webView.webChromeClient = WebChromeClient()
////        holder.webView.webViewClient = WebViewClient()
////        }
//
//    override fun getItemCount(): Int {
//        return links.size
//        }
//
//    class LinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val webView: WebView = itemView.findViewById(R.id.webView)
//
////        init {
////            // Remove margins and padding programmatically
////            val layoutParams = itemView.layoutParams as RecyclerView.LayoutParams
////            layoutParams.setMargins(0, 0, 0, 0) // Adjust margins as needed
////            itemView.layoutParams = layoutParams
////            itemView.setPadding(0, 0, 0, 0) // Adjust padding as needed
////        }
//
////        val textViewLink: TextView = itemView.findViewById(R.id.textViewLink)
//        }
//    }
