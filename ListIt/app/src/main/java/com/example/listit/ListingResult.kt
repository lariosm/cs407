package com.example.listit

import com.google.gson.annotations.SerializedName

data class ListingResult(
    @SerializedName("listing_id") val id: Float,
    @SerializedName("username") val userName: String,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("asking_price") val askingPrice: Int,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("image_url") val imageURL: String
)