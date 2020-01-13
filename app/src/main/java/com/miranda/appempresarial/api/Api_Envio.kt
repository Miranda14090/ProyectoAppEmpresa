package com.miranda.appempresarial.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api_Envio {
    /*"https://rompope.fi-b.unam.mx:8443/"*/   /*"http://192.168.15.56:8080/"*/

    private val BASE_URL =  "http://10.95.71.24:8080/"

    private lateinit var retrofit: Retrofit

    fun getApiEnvio(): Retrofit {

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit
    }
    fun getApiEnvioTmp():Retrofit {

        retrofit = Retrofit.Builder()
            .baseUrl( "http://10.95.71.10:8070/semillero/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}