package com.isd.isd

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class Navigation_Notifications : Fragment() {

    private lateinit var recyclerView: RecyclerView

//    private lateinit var dataList : ArrayList<Data>



    private lateinit var headerList : ArrayList<String>
    private lateinit var descriptionList : ArrayList<String>
    private lateinit var dateList : ArrayList<String>
    private lateinit var timeList : ArrayList<String>

    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        headerList = arrayListOf(
//            "header1",
//            "header2"
//        )
//
//        descriptionList = arrayListOf(
//            "lorem ipsum1",
//            "lorem ipsum2"
//        )
//        dateList = arrayListOf(
//            "01/01/2001",
//            "02/02/2002"
//        )
//        timeList = arrayListOf(
//            "01:00",
//            "02:00"
//        )


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_navigation__notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val ShimmerFrameLayout = view.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container4)

//        recyclerView = view.findViewById(R.id.notif_recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
//        recyclerView.setHasFixedSize(true)
//
//        dataList = arrayListOf<Data>()
//        getdata()

        Log.e("ASD", "View Created")

        recyclerView = view.findViewById(R.id.notif_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
//        recyclerView.setHasFixedSize(true)
        Log.e("ASD", "took Activity")

        val adapter = Adapter(ArrayList()) // Initialize adapter with empty list
        recyclerView.adapter = adapter
        Log.e("ASD", "Adapter Set up")

        lifecycleScope.launch {
            try {
                val dataList = fetchNotifications()
                adapter.updateList(dataList)
//                ShimmerFrameLayout.stopShimmer()
//                ShimmerFrameLayout.hideShimmer()
            } catch (error: Exception) {
                // Handle error (e.g., log a message, display an error UI)
                Log.e("ASD", "Error fetching notifications: $error")
            }
        }

    }

//    private fun getdata(){
//        for (i in headerList.indices){
//            val Data = Data(headerList[i],descriptionList[i],dateList[i],timeList[i])
//            dataList.add(Data)
//        }
//        recyclerView.adapter = Adapter(dataList)
//    }

    private suspend fun fetchNotifications(): ArrayList<Data1> {
        val notificationsRef = firestore.collection("notifications")

        val querySnapshot = notificationsRef.get().await() // Use await() for coroutines

        val dataList = ArrayList<Data1>()
        for (document in querySnapshot) {
            val notification = document.toObject(Notification::class.java) ?: continue
            dataList.add(Data1(notification.header!!, notification.description!!, notification.date!!, notification.time!!))
        }

        return dataList
    }


    // Data class to hold notification fields (modify if needed)
    data class Notification(
        val header: String? = null,
        val description: String? = null,
        val date: String? = null,
        val time: String? = null
    )

//    // Data class for display (update structure if necessary)
//    data class Data(
//        val header: String,
//        val description: String,
//        val date: String,
//        val time: String
//    )

//    // Adapter class to handle data display (implement based on your requirements)
//    class Adapter(private var dataList: ArrayList<Data>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
//
//        // Update internal data list
//        fun updateList(newList: ArrayList<Data>) {
//            dataList = newList
//            notifyDataSetChanged()
//        }
//
//        // ... (rest of Adapter implementation)
//    }

}