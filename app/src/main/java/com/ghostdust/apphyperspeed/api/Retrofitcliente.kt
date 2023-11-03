package com.ghostdust.apphyperspeed.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofitcliente {
    fun getClient(url: String): Retrofit {
        return Retrofit. Builder()
            .baseUrl(url)
            .addConverterFactory (GsonConverterFactory.create())
            .build()
    }
}