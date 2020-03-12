package com.example.listit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("api/listings")
    fun getListingResults(
        // @Query("limit") results: Int = 20,
        // @Query("offset") offset: Int
    ): Call<GetListingResponse>
}