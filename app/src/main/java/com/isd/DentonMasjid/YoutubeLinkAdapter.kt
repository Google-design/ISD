package com.isd.DentonMasjid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    override fun getItemCount(): Int {
        return links.size
    }

    inner class LinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}

