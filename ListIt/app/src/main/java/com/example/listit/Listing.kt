package com.example.listit

import com.google.gson.annotations.SerializedName

data class Listing(
    @SerializedName("id") val id: String,
    @SerializedName("username") val userName: String,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("listing_title") val listingTitle: String,
    @SerializedName("description") val description: String,
    @SerializedName("asking_price") val askingPrice: Int,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String
    // @SerializedName("image_url") val imageURL: String
)