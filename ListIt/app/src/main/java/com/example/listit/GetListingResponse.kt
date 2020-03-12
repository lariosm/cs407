package com.example.listit

import com.google.gson.annotations.SerializedName

data class GetListingResponse(
    @SerializedName("listings") val listings: List<Listing>
)