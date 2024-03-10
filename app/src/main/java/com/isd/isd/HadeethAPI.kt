package com.isd.isd

import com.isd.isd.HadeethDataClasses.HadeethData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface HadeethAPI {
    @GET()
    suspend fun getHadeeth(@Url url: String): Response<HadeethData>
}