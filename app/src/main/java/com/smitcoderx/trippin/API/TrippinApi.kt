package com.smitcoderx.trippin.API

import com.smitcoderx.trippin.Model.Auth.Login
import com.smitcoderx.trippin.Model.Auth.Register
import com.smitcoderx.trippin.Model.Places.Places
import com.smitcoderx.trippin.Model.Review.Reviews
import com.smitcoderx.trippin.Model.User.User
import retrofit2.Response
import retrofit2.http.*

interface TrippinApi {

    @POST("login")
    suspend fun loginUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<Login>

    @POST("user_register")
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

    @GET("get_reviews/{id}")
    suspend fun getReviews(
        @Path("id") id: String
    ): Response<Reviews>

    @POST("post_reviews")
    suspend fun postReview(
        @Header("x-access-token") token: String,
        @Query("business_id") businessId: String,
        @Query("review") review: String,
        @Query("ratings") rating: Float
    ): Response<Register>
}