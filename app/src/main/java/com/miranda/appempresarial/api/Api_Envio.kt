package com.miranda.appempresarial.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api_Envio {

    private val BASE_URL = "http://10.95.71.18:8080/"
    private lateinit var retrofit: Retrofit

    fun getApiEnvio(): Retrofit {

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit
    }
}