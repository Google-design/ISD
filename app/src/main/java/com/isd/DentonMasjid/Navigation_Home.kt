package com.isd.DentonMasjid

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.isd.DentonMasjid.HadeethDataClasses.HadeethData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.random.Random
import com.facebook.shimmer.ShimmerFrameLayout

// Inside your activity or fragment class



class Navigation_Home : Fragment() {

    private lateinit var hadeeth_tv: TextView
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

        val textView4 = view.findViewById<TextView>(R.id.textView4)
        val addressText4 = "Address: 1105 Green Lee St Denton, TX 76201"
        val onlyAddressText4 = "1105 Green Lee St Denton, TX 76201"
        val text = addressText4
        val spannableString = SpannableString(text)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Create an intent to open Google Maps
                val gmmIntentUri =   Uri.parse("geo:0,0?q=${Uri.encode(onlyAddressText4)}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")

                // Verify that the intent will resolve to an activity
                if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(mapIntent)
                } else {
                    // Handle case where Google Maps app is not installed
                    val clipboard = ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
                    val clip = ClipData.newPlainText("address", onlyAddressText4)

                    clipboard?.setPrimaryClip(clip)

                    Toast.makeText(requireContext(), "Address copied to clipboard", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Set the clickable span to the text view
        spannableString.setSpan(
            clickableSpan,
            9,
            text.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView4.text = spannableString
        textView4.movementMethod = LinkMovementMethod.getInstance()

        val textView5 = view.findViewById<TextView>(R.id.textView5)
        val text5 = textView5.text
        val spannableString5 = SpannableString(text5)

        // Create ClickableSpan for the email address
        val emailClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle email click event here
                val emailIntent = Intent(Intent.ACTION_SENDTO)
                val email = "dentonmosque@gmail.com"
                emailIntent.data = Uri.parse("mailto:dentonmosque@gmail.com")
                if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(emailIntent)
                }
                else {
                    val clipboard = ContextCompat.getSystemService(
                        requireContext(),
                        ClipboardManager::class.java
                    )
                    val clip = ClipData.newPlainText("email", email)

                    clipboard?.setPrimaryClip(clip)

                    Toast.makeText(
                        requireContext(),
                        "Email copied to clipboard",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        spannableString5.setSpan(
            emailClickableSpan,
            7,
            29,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView5.text = spannableString5
        textView5.movementMethod = LinkMovementMethod.getInstance()

        // Create ClickableSpan for the phone number
        val textView6 = view.findViewById<TextView>(R.id.textView6)
        val text6 = textView6.text
        val spannableString6 = SpannableString(text6)
        val phoneClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle phone click event here
                val phoneIntent = Intent(Intent.ACTION_DIAL)
                val phoneNumeber = "940-484-1871"
                phoneIntent.data = Uri.parse("tel:940-484-1871")
                if (phoneIntent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(phoneIntent)
                }
                else {
                    // Handle case where Google Maps app is not installed
                    val clipboard = ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
                    val clip = ClipData.newPlainText("phone", phoneNumeber)

                    clipboard?.setPrimaryClip(clip)

                    Toast.makeText(requireContext(), "Phone number copied to clipboard", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // Adjust the start and end indices based on your text
        spannableString6.setSpan(
            phoneClickableSpan,
            7, // Start index of the phone number
            21, // End index of the phone number
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView6.text = spannableString6
        textView6.movementMethod = LinkMovementMethod.getInstance()

        hadeeth_tv = view.findViewById(R.id.hadeeth_tv)

        val donateButton: Button = view.findViewById(R.id.donate_button)
        donateButton.setOnClickListener {
            val url = "https://www.dentonmosque.com/donate/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        shimmerFrameLayout= view.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container1)
        shimmerFrameLayout.visibility = View.VISIBLE
        shimmerFrameLayout.startShimmer()

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
}