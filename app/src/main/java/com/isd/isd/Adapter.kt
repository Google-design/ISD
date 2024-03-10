package com.isd.isd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout

class Adapter(private var dataList: ArrayList<Data1>): RecyclerView.Adapter<Adapter.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolderClass(itemView)

    }

    override fun getItemCount(): Int {
        return dataList.size

    }

    fun updateList(newList: ArrayList<Data1>) {
            dataList = newList
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]

        holder.header.text = currentItem.header
        holder.description.text = currentItem.description
        holder.date.text = currentItem.date
        holder.time.text = currentItem.time
    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {

        val header:TextView = itemView.findViewById(R.id.header_tv)
        val description: TextView = itemView.findViewById(R.id.description_tv)
        val date: TextView = itemView.findViewById(R.id.date_tv)
        val time: TextView = itemView.findViewById(R.id.time_tv)


    }
}