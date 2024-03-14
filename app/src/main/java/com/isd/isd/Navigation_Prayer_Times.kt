package com.isd.isd

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.findViewTreeFullyDrawnReporterOwner
import androidx.lifecycle.lifecycleScope
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class Navigation_Prayer_Times : Fragment() {

    private lateinit var countdownTimer: TextView
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private var URL = "https://api.aladhan.com"
    private val TAG: String = "CHECK_RESPONSE"
    private lateinit var date: String

    //date is in the format: DD-MM-YYYY
    private val coords_with_method =
        "?latitude=33.201662695006874&longitude=-97.14494994434574&method=2"

    private lateinit var adhan : Adhan


    // Textviews Adhan
    private lateinit var fajradhan: TextView
    private lateinit var zuhradhan: TextView
    private lateinit var asradhan: TextView
    private lateinit var maghribadhan: TextView
    private lateinit var ishaadhan: TextView
//    private lateinit var sunrise: TextView

    // Textviews Iqama

    private lateinit var fajrtime: TextView
    private lateinit var zuhrtime: TextView
    private lateinit var asrtime: TextView
    private lateinit var maghribtime: TextView
    private lateinit var jummahtime: TextView
    private lateinit var ishatime: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation__prayer__times, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container2)

        countdownTimer = view.findViewById(R.id.countdown_timer)
        fajradhan = view.findViewById(R.id.fajradhan)
        zuhradhan = view.findViewById(R.id.zuhradhan)
        asradhan = view.findViewById(R.id.asradhan)
        maghribadhan = view.findViewById(R.id.maghribadhan)
        ishaadhan = view.findViewById(R.id.ishaadhan)
//        sunrise = view.findViewById(R.id.Sunrisetime)

        fajrtime = view.findViewById(R.id.fajrtime)
        zuhrtime = view.findViewById(R.id.zuhrtime)
        asrtime = view.findViewById(R.id.asrtime)
        maghribtime = view.findViewById(R.id.maghribtime)
        ishatime = view.findViewById(R.id.ishatime)
        jummahtime = view.findViewById(R.id.jummahtime)


        // Launch coroutines for asynchronous tasks
        lifecycleScope.launch {
            // Get date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // 0-indexed
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            date = String.format("%02d-%02d-%04d", day, month, year)

            Log.d(TAG, "date : ${date}")


            launch(Dispatchers.IO) {
                // Get AdhanTimes
                getAdhanTimes {
                    Log.d(TAG, "Gotadhantimes")
                    getIqamaDataFromFirebase()


                }
                Log.d(TAG, "Run successful: iqamadata")
            }


            // Update UI on the main thread
            withContext(Dispatchers.Main) {
                val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                val backgroundImage = when {
                    currentTime in 6..17 -> R.drawable.day
                    else -> R.drawable.night
                }
                requireView().setBackgroundResource(backgroundImage)

                // Start countdown (consider using separate coroutine)
                // ...

                view.alpha = 0.5f
            }
        }
        Log.d("TAG1", "countdown started")
        view.alpha = 0.7f
    }

    private fun getIqamaDataFromFirebase() {
        lifecycleScope.launch {
            val db = FirebaseFirestore.getInstance()
            val iqamascoll = db.collection("iqama_time_period")
            val iqamasdoc = iqamascoll.document("iqamas")

            iqamasdoc.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val iqamaPeriod = document.toObject(IqamaPeriod::class.java)
                        Log.d(TAG, "doc exists")
                        if (iqamaPeriod != null) {
                            Log.d(TAG, "Fajr: ${iqamaPeriod.fajr}")

                            fajrtime.text =
                                addTimes(iqamaPeriod.fajr.toString(), fajradhan.text.toString())
                            zuhrtime.text =
                                addTimes(iqamaPeriod.duhr.toString(), zuhradhan.text.toString())
                            asrtime.text =
                                addTimes(iqamaPeriod.asr.toString(), asradhan.text.toString())
                            maghribtime.text =
                                addTimes(iqamaPeriod.maghrib.toString(), maghribadhan.text.toString())
                            ishatime.text =
                                addTimes(iqamaPeriod.isha.toString(), ishaadhan.text.toString() )

                            Log.d(TAG, "Parse done")

                            shimmerFrameLayout.stopShimmer()
                            shimmerFrameLayout.hideShimmer()

                            // Call getAdhanTimes() in callback if parsing is successful
                        }
                    } else {
                        Log.d(TAG, "No such document!")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting document:", exception)
                }
        }
    }



    // Function to call getAdhanTimes() only after parsing is complete
    suspend fun getAdhanTimes(completionCallback: () -> Unit){
        Log.d(TAG, "$URL/v1/timings/$date$coords_with_method")
        lifecycleScope.launch {
            try {
                val response =
                    RetrofitInstance.api.getAdhan("$URL/v1/timings/$date$coords_with_method")
                if (response.isSuccessful) {
                    val Adhans = response.body()
                    adhan = response.body()!!
                    if (Adhans != null) {
                        Log.d(TAG, "adhan successful")
                        Log.i(TAG, "Adhan: ${Adhans.data.timings.Fajr}")
                        fajradhan.text = convertTime(Adhans.data.timings.Fajr)
                        zuhradhan.text = convertTime(Adhans.data.timings.Dhuhr)
                        asradhan.text = convertTime(Adhans.data.timings.Asr)
                        maghribadhan.text = convertTime(Adhans.data.timings.Maghrib)
                        ishaadhan.text = convertTime(Adhans.data.timings.Isha)
//                        sunrise.text = convertTime(Adhans.data.timings.Sunrise)

                        // Call the completion callback when Adhan data is retrieved
                        completionCallback()
                    } else {
                        Log.w(TAG, "Response body is null")
                    }
                } else {
                    Log.e(TAG, "API call failed: ${response.code()} ${response.message()}")
                    // Handle the error appropriately
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching Adhan data", e)
                // Handle generic exceptions
            }

        }
    }

    fun convertTime(time: String): String {
        val timeParts = time.split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour) // Use HOUR_OF_DAY instead of HOUR
        calendar.set(Calendar.MINUTE, minute)

        val amPm = if (calendar.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
        var formattedHour = (calendar.get(Calendar.HOUR_OF_DAY) % 12).toString().padStart(2, '0')

        // Handle 12:00 PM case separately
        if (formattedHour == "00" && amPm == "PM") {
            formattedHour = "12"
        }

        val formattedMinute = minute.toString().padStart(2, '0')

        return "$formattedHour:$formattedMinute $amPm"
    }


    fun addTimes(time1: String, time2: String): String {
        // Validate time2 format
        val time2Regex = Regex("""^(\d{1,2}):(\d{2}) (AM|PM)$""")
        if (!time2Regex.matches(time2)) {
            throw IllegalArgumentException("time2 must be in 12-hour format (HH:MM AM|PM)")
        }

        // Extract hours and minutes from each time string
        val timeParts1 = time1.split(":")
        val hour1 = timeParts1[0].toInt()
        val minute1 = timeParts1[1].toInt()

        val timeParts2 = time2.split(":")
        val hour2 = timeParts2[0].toInt()
        val minute2 = timeParts2[1].substringBefore(' ').toInt() // Extract minutes
        val amPm2 = timeParts2[1].substringAfter(' ') // Extract AM/PM

        // Calculate total minutes
        var totalMinutes = hour1 * 60 + minute1
        if (amPm2 == "PM") {
            totalMinutes += 12 * 60 // Add 12 hours if it's PM
        }
        totalMinutes += hour2 * 60 + minute2

        // Calculate new hour and minute values
        val newHour = (totalMinutes / 60) % 24 // Convert minutes back to hours (with 24-hour cycle)
        val newMinute = totalMinutes % 60 // Remaining minutes

        // Format AM/PM
        val newAmPm = if (newHour >= 12) "PM" else "AM"
        val formattedHour = if (newHour == 0 || newHour == 12) "12" else (newHour % 12).toString().padStart(2, '0')

        // Return the sum in HH:MM AM/PM format
        return "$formattedHour:${newMinute.toString().padStart(2, '0')} $newAmPm"
    }
}