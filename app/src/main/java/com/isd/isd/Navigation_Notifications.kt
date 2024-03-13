package com.isd.isd

import android.app.DownloadManager
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.text.isNullOrEmpty

class Navigation_Notifications : Fragment(), Adapter.DownloadClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var storageReference: FirebaseStorage
    private lateinit var firestore: FirebaseFirestore
    private var dataList = ArrayList<Data1>() // Declare dataList here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        Log.e("ASD", "View Created")

        recyclerView = view.findViewById(R.id.notif_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        storageReference = FirebaseStorage.getInstance()

        Log.e("ASD", "took Activity")

        val adapter = Adapter(dataList, this) // Pass dataList to the adapter
        recyclerView.adapter = adapter
        Log.e("ASD", "Adapter Set up")

        lifecycleScope.launch {
            try {
                fetchNotifications()
                adapter.notifyDataSetChanged() // Notify adapter about data change

            } catch (error: Exception) {
                // Handle error (e.g., log a message, display an error UI)
                Log.e("ASD", "Error fetching notifications: $error")
            }
        }
    }

    override fun onDownloadClick(position: Int) {
        val currentItem = dataList[position]
        val imageBitmap = currentItem.imgpath // Assuming imgpath is a Bitmap object

        // Check if imageBitmap is not null
        if (imageBitmap != null) {
            // Create a filename for the downloaded image
            val fileName = "image_${System.currentTimeMillis()}.jpg"

            // Get the directory to save the downloaded image
            val downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            // Create a file to save the image
            val imageFile = File(downloadDirectory, fileName)

            try {
                // Create an output stream to write the bitmap data to the file
                val outputStream = FileOutputStream(imageFile)
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()

                // Notify the system that a new file has been created and it should be scanned for media
                MediaScannerConnection.scanFile(requireContext(), arrayOf(imageFile.path), null, null)

                // Display a Toast to notify the user about the successful download
                Toast.makeText(requireContext(), "Image downloaded successfully", Toast.LENGTH_SHORT).show()

                // Open the Downloads app to show the downloaded image
                val intent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } catch (e: IOException) {
                // Handle exceptions related to file operations
                Log.e("Navigation_Notifications", "Error saving image: ${e.message}")
                Toast.makeText(requireContext(), "Failed to download image", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Handle null imageBitmap
            Toast.makeText(requireContext(), "Invalid image", Toast.LENGTH_SHORT).show()
        }
    }



    private suspend fun fetchNotifications() {
        val notificationsRef = firestore.collection("notifications")
        val querySnapshot = notificationsRef.get().await() // Use await() for coroutines
        for (document in querySnapshot) {
            val notification = document.toObject(Notification::class.java) ?: continue
            val imageBitmap = if (notification.imgpath != null) {
                downloadImage(notification.imgpath)
            } else {
                null
            }
            dataList.add(
                Data1(
                    notification.header!!,
                    notification.description!!,
                    notification.date!!,
                    notification.time!!,
                    imageBitmap
                )
            )
        }
    }

    private suspend fun downloadImage(imageUrl: String): Bitmap? {
        return try {
            val imageRef = storageReference.reference.child(imageUrl)
            val ONE_MEGABYTE: Long = 1024 * 1024
            val bytes = imageRef.getBytes(ONE_MEGABYTE).await()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            Log.e("ASD", "Error downloading image: $e")
            null
        }
    }

    // Data class to hold notification fields (modify if needed)
    data class Notification(
        val header: String? = null,
        val description: String? = null,
        val date: String? = null,
        val time: String? = null,
        val imgpath: String? = null,
    )
}
