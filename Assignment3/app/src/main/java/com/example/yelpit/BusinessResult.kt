package com.example.yelpit

import com.google.gson.annotations.SerializedName

//GET: /businesses/search
data class BusinessResult(
    @SerializedName("rating") val rating: Double,
    @SerializedName("id") val id: String,
    @SerializedName("is_closed") val isClosed: Boolean,
    @SerializedName("review_count") val reviewCount: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageURL: String
)