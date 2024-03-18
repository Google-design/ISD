package com.isd.DentonMasjid

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PrayerAPI {

    @GET()
    suspend fun getAdhan(@Url url: String): Response<Adhan>

}