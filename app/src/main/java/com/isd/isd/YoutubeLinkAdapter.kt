package com.isd.isd

//class YoutubeLinkAdapter {
//     YoutubeLinkAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
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
//        holder.textViewLink.text = link
        holder.webView.loadData(link,"text/html","utf-8")
        holder.webView.settings.javaScriptEnabled = true
        holder.webView.webChromeClient = WebChromeClient()
        holder.webView.webViewClient = WebViewClient()
        holder.webView.loadUrl(link)
        }

    override fun getItemCount(): Int {
        return links.size
        }

    inner class LinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val webView: WebView = itemView.findViewById(R.id.webView)
//        val textViewLink: TextView = itemView.findViewById(R.id.textViewLink)
        }
    }
