package com.ghostdust.apphyperspeed.api

import com.ghostdust.apphyperspeed.Rutas.usuariorutas
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRutas {
    val API_URL = "http://192.168.1.105:3000/api/"

    // Inicializa Retrofit
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getUsersRoutes(): usuariorutas {
        return retrofit.create(usuariorutas::class.java)
    }
}
