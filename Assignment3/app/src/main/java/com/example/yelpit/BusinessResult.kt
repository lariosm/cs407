package com.example.yelpit

import com.google.gson.annotations.SerializedName

//GET: /businesses/search
data class BusinessResult(
    @SerializedName("rating") val rating: Float,
    @SerializedName("phone") val phone: String,
    @SerializedName("id") val id: String,
    @SerializedName("review_count") val reviewCount: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageURL: String,
    @SerializedName("location") val location: BusinessLocation
)

data class BusinessLocation(
    @SerializedName("city") val city: String,
    @SerializedName("country") val country: String,
    @SerializedName("state") val state: String,
    @SerializedName("address1") val address: String,
    @SerializedName("zip_code") val zipCode: String
)