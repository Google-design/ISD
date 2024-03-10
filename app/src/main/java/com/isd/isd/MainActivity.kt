package com.isd.isd

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.isd.isd.databinding.ActivityMainBinding
import androidx.activity.compose.setContent
import androidx.appcompat.app.ActionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.isd.isd.ui.theme.ISDTheme
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



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



        // Replace with the appropriate binding class for your layout
//        setContent {
//            ISDTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }
//        }
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

}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ISDTheme {
//        Greeting("Android")
//    }
//}