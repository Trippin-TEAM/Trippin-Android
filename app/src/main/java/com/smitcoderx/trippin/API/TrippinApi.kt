package com.smitcoderx.trippin.API

import com.smitcoderx.trippin.Model.Auth.Login
import com.smitcoderx.trippin.Model.Auth.Register
import com.smitcoderx.trippin.Model.Image.UploadImage
import com.smitcoderx.trippin.Model.Places.Places
import com.smitcoderx.trippin.Model.Review.Reviews
import com.smitcoderx.trippin.Model.User.UpdateUser
import com.smitcoderx.trippin.Model.User.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("upload_images")
    suspend fun sendImage(
        @Header("x-access-token") token: String,
        @Part file: MultipartBody.Part
    ): Response<UploadImage>

    @Headers("Content-Type: application/json")
    @PUT("update")
    suspend fun updateUser(
        @Header("x-access-token") token: String,
        @Body update: RequestBody
    ): Response<UpdateUser>
}