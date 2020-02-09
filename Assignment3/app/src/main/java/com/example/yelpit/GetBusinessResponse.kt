package com.example.yelpit

import com.google.gson.annotations.SerializedName

//GET: /businesses/search
data class GetBusinessResultResponse(
    @SerializedName("businesses") val businesses: List<BusinessResult>
)

//GET: /businesses/{id}
data class GetBusinessResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("is_closed") val isClosed: Boolean,
    @SerializedName("display_phone") val phone: String,
    @SerializedName("review_count") val reviewCount: Int,
    @SerializedName("rating") val rating: Double,
    @SerializedName("photos") val imageListURL: List<String>
)