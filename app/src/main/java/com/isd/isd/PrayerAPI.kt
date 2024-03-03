package com.isd.isd

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url
import javax.annotation.processing.Generated

interface PrayerAPI {

    @GET()
    suspend fun getAdhan(@Url url: String): Response<Adhan>

}