package com.isd.isd

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

object RetrofitInstance {

    val api: PrayerAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.aladhan.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PrayerAPI::class.java)
    }
}