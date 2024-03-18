package com.isd.DentonMasjid

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HadeethInstance {
    val h_api: HadeethAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://hadeethenc.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HadeethAPI::class.java)
    }
}