package com.smitcoderx.trippin.API

import com.smitcoderx.trippin.Model.Login
import com.smitcoderx.trippin.Model.Register
import com.smitcoderx.trippin.Model.User
import retrofit2.Response
import retrofit2.http.*

interface TrippinApi {

    companion object {
        const val BASE_URL = "https://trippin-users.herokuapp.com/"
    }

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
    ) : Response<User>

}