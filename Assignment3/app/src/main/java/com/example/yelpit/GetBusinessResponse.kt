package com.example.yelpit

import com.google.gson.annotations.SerializedName

//GET: /businesses/search
data class GetBusinessResultResponse(
    @SerializedName("businesses") val businesses: List<BusinessResult>,
    @SerializedName("location") val businessLocation: List<BusinessLocation>
)