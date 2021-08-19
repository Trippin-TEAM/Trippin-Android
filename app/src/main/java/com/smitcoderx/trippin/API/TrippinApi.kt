package com.smitcoderx.trippin.API

import com.smitcoderx.trippin.Model.Auth.Login
import com.smitcoderx.trippin.Model.Auth.Register
import com.smitcoderx.trippin.Model.Places.Places
import com.smitcoderx.trippin.Model.User.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TrippinApi {

    @POST("login")
    suspend fun loginUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<Login>

    @POST("register")
    suspend fun registerUser(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("mobile_no") mobileNo: String
    ): Response<Register>

    @GET("me")
    suspend fun getMe(
        @Header("x-access-token") token: String
    ): Response<User>

    @GET("places")
    suspend fun getPlaces(
        @Query("city") city: String
    ): Response<Places>
}