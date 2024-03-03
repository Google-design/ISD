package com.isd.isd

import com.isd.isd.JsonDataClasses.Data
import com.isd.isd.JsonDataClasses.Meta
import com.isd.isd.JsonDataClasses.Timings
import java.util.Date

data class Adhan(val code: Int,
                 val `data`: Data,
                 val status: String
)
//data class Data(
//    val date: com.isd.isd.JsonDataClasses.Date,
//    val meta: Meta,
//    val timings: Timings
//)

//data class Timings (
//    val Fajr: String? = null,
//    val Sunrise: String? = null,
//    val Dhuhr: String? = null,
//    val Asr: String? = null,
//    val Sunset: String? = null,
//    val Maghrib: String? = null,
//    val Isha: String? = null,
////
////    @SerialName("Imsak")
////    val imsak: String,
////
////    @SerialName("Midnight")
////    val midnight: String,
////
////    @SerialName("Firstthird")
////    val firstthird: String,
////
////    @SerialName("Lastthird")
////    val lastthird: String
//)

