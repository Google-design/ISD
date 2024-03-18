package com.isd.DentonMasjid

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: PrayerAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.aladhan.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PrayerAPI::class.java)
    }
}