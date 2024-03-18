package com.isd.DentonMasjid

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import kotlinx.coroutines.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ViewBinding
    private lateinit var connectivityManager: ConnectivityManager
    private val isConnectedFlow = MutableStateFlow(false)
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        startNetworkMonitoring()

        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        actionBar?.hide()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(androidx.navigation.fragment.R.id.nav_host_fragment_container)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_Home,R.id.navigation_Prayer_Times,R.id.navigation_Calendar,R.id.navigation_videos,R.id.navigation_Notifications))
        // R.id.navigaion_Settings
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        bottomNavigationView.setupWithNavController(navController)
        subscribeToTopic()

        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            // Prompt the user to enable notifications
            showNotificationPermissionDialog()
        }
    }

    fun subscribeToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("ISD.channel.id")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("T","success")
                    // Subscription successful
                    // You can perform any additional logic or display a success message here
                } else {
                    Log.d("T","failed")
                    // Subscription failed;74
                    // You can handle the failure case or display an error message here
                }
            }
    }

    private fun showNotificationPermissionDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Notification Permission Required")
        alertDialogBuilder.setMessage("Please enable notifications to continue.")
        alertDialogBuilder.setPositiveButton("Settings") { dialogInterface: DialogInterface, i: Int ->
            // Open app notification settings
            val intent = Intent().apply {
                action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
            startActivity(intent)
            dialogInterface.dismiss()
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
            // Handle cancellation
            dialogInterface.dismiss()
            // Allow the app to be used
            // You may handle this based on your app's requirements
        }
        alertDialogBuilder.setCancelable(false)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun startNetworkMonitoring() {
        job = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val isConnected = isNetworkConnected()
                isConnectedFlow.emit(isConnected)
                delay(1000) // Check every second
            }
        }

        // Observe network connectivity changes
        CoroutineScope(Dispatchers.Main).launch {
            isConnectedFlow
                .collect { isConnected ->
                    if (!isConnected) {
                        // Inform the user when the phone is not connected to the internet
                        // For example, you can show a toast message
                        // Replace "this@MainActivity" with your activity reference
                        // Replace "No Internet Connection" with your desired message
                        showToast("No Internet Connection, please connect to the Internet")
                    }
                }
        }
    }

    private fun isNetworkConnected(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun showToast(message: String) {
        runOnUiThread {
            // Show a toast message
            // Replace "this@MainActivity" with your activity reference
            // Replace "No Internet Connection" with your desired message
            // Replace "Toast.LENGTH_SHORT" with your desired duration
            android.widget.Toast.makeText(this@MainActivity, message, android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel() // Cancel the coroutine job when activity is destroyed
    }



}
