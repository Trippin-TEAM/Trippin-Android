package com.smitcoderx.trippin.Model.Places

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlacesItem(
    val _id: String,
    val address: String,
    val average_ratings: Double,
    val city: String,
    val desc: String,
    val email: String,
    val image: String,
    val mobile_no: String,
    val name: String,
    val type: String
) : Parcelable