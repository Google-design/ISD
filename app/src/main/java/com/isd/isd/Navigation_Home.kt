package com.isd.isd

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2

import org.json.JSONException
import java.util.Collections.min
import kotlin.math.min


class Navigation_Home : Fragment() {


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


}