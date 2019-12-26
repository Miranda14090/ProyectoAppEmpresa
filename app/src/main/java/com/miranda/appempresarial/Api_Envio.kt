package com.miranda.appempresarial

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api_Envio {

    private val BASE_URL = "http://10.95.71.18:8080/"
    private lateinit var retrofit: Retrofit

    fun getApiEnvio(): Retrofit {

        if (retrofit == null) {

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}