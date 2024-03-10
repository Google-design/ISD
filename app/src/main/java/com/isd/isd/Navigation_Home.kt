package com.isd.isd

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.isd.isd.HadeethDataClasses.HadeethData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.random.Random
import org.json.JSONException
import java.util.Collections.min
import kotlin.math.min
import com.facebook.shimmer.ShimmerFrameLayout

// Inside your activity or fragment class



class Navigation_Home : Fragment() {

    private lateinit var hadeeth_tv : TextView
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private var URL = "https://hadeethenc.com"
    private val TAG: String = "CHECK_RESPONSE"
    private lateinit var hadeethData: HadeethData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation__home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hadeeth_tv = view.findViewById(R.id.hadeeth_tv)

        shimmerFrameLayout= view.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container1)

        shimmerFrameLayout.visibility = View.VISIBLE
        shimmerFrameLayout.startShimmer()
//        shimmerFrameLayout.visibility = View.VISIBLE
//        lifecycleScope.launch {
//            shimmerFrameLayout.startShimmer()
//        }



        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Get Hadeeth
                getHadeeth {
                    Log.d(TAG, "Got Hadeeth")
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.hideShimmer()
                }
                Log.d(TAG, "Run successful: Hadeeth acquired")
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching Hadeeth data", e)
                // Handle the error appropriately
            }
        }

        // Find the ImageButtons in your layout
        val facebookButton = view.findViewById<ImageButton>(R.id.fbbutton)
        val instagramButton = view.findViewById<ImageButton>(R.id.instabutton)
//        val twitterButton = view.findViewById<ImageButton>(R.id.twitterbutton)
        val youtubeButton = view.findViewById<ImageButton>(R.id.ytbutton)

        facebookButton.setOnClickListener {
            openInAppIfPossible("fb://page/201875533168156", 1)
        }

        instagramButton.setOnClickListener {
            openInAppIfPossible("instagram://user?username=dentonmasjid", 2)
        }

//        twitterButton.setOnClickListener {
//            openInAppIfPossible("twitter://user?screen_name=dentonmosque", 3)
//        }

        youtubeButton.setOnClickListener {
            openInAppIfPossible("youtube://user/isd1105", 4)
        }
    }
    private fun openInAppIfPossible(uriString: String, site: Int) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
        val activity = requireActivity() // Get the reference to the parent activity
        if (intent.resolveActivity(activity.packageManager) != null) {
            startActivity(intent)
        } else if (site == 1) {
            openWebPage("http://facebook.com/Islamic-Society-of-Denton-201875533168156/") // Fallback to opening in a web browser
        }
        else if(site == 2) {
            openWebPage("https://www.instagram.com/dentonmasjid/?img_index=1")
        }
        else if(site == 3){
            openWebPage("http://twitter.com/dentonmosque")
        }
        else if (site == 4){
            openWebPage("http://youtube.com/user/isd1105/featured")
        }
    }

    private fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun generateRandomNumber(): Int {
        val calendar = Calendar.getInstance()
        val seed = calendar.get(Calendar.DAY_OF_YEAR) + calendar.get(Calendar.YEAR)
        val random = Random(seed)
        return random.nextInt(2962, 3100) // 3391 is exclusive, so it will generate numbers up to 3390
    }

    suspend fun getHadeeth(completionCallback: () -> Unit) {

        // Generate a random number for id_hadeeth
        val id_hadeeth = generateRandomNumber()

        Log.d(TAG, "$URL/api/v1/hadeeths/one/?language=en&id=$id_hadeeth")
        lifecycleScope.launch {
            try {
                val response =
                    HadeethInstance.h_api.getHadeeth("$URL/api/v1/hadeeths/one/?language=en&id=$id_hadeeth")
                if (response.isSuccessful) {
                    val hadeethData = response.body()
                    if (hadeethData != null) {
                        Log.d(TAG, "Hadeeth retrieved")
                        Log.i(TAG, "Hadeeth: ${hadeethData.hadeeth.toString()}")
                        hadeeth_tv.text = hadeethData.hadeeth.toString()

                        completionCallback()
                    } else {
                        Log.w(TAG, "Response body is null")
                    }
                } else {
                    Log.e(TAG, "API call failed: ${response.code()} ${response.message()}")
                    // Handle the error appropriately
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching Hadeeth data", e)
                // Handle generic exceptions
            }
        }
    }


//    suspend fun getHadeeth(completionCallback: () -> Unit) {
//
//        Log.d(TAG, "$URL/api/v1/hadeeths/one/?language=en&id=$id_hadeeth")
//        lifecycleScope.launch {
//            try {
//                val response =
//                    HadeethInstance.h_api.getHadeeth("$URL/api/v1/hadeeths/one/?language=en&id=$id_hadeeth")
//                if (response.isSuccessful) {
//                    val Hadeeths = response.body()
//                    hadeethData = response.body()!!
//                    if (hadeethData != null) {
//                        Log.d(TAG, "hadeeth retrieved")
//                        if (Hadeeths != null) {
//                            Log.i(TAG, "Hadeeth: ${Hadeeths.hadeeth.toString()}")
//                            hadeeth_tv.text = Hadeeths.hadeeth.toString()
//                        }
//
//
//                        // Call the completion callback when Adhan data is retrieved
//                        completionCallback()
//                    } else {
//                        Log.w(TAG, "Response body is null")
//                    }
//                } else {
//                    Log.e(TAG, "API call failed: ${response.code()} ${response.message()}")
//                    // Handle the error appropriately
//                }
//            } catch (e: Exception) {
//                Log.e(TAG, "Error fetching Adhan data", e)
//                // Handle generic exceptions
//            }
//
//        }
//
//
//
//    }


}