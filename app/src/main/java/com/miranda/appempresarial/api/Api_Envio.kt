package com.miranda.appempresarial.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api_Envio {


    private val BASE_URL =  "http://132.248.59.72:8080/" /*"http://10.95.71.25:8080/"*/ /*"https://rompope.fi-b.unam.mx:8443/"*/

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