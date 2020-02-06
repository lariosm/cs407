package com.example.yelpit

import com.google.gson.annotations.SerializedName

data class GetBusinessResultResponse(
    @SerializedName("businesses") val businesses: List<BusinessResult>
)