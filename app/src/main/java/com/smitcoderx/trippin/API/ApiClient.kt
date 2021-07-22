package com.smitcoderx.trippin.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://trippin-users.herokuapp.com/"

    private val retrofit: Retrofit by lazy{
         Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val retrofitService by lazy {
        retrofit.create(TrippinApi::class.java)
    }

}