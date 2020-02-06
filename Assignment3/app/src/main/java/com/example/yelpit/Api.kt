package com.example.yelpit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @GET("businesses/search")
    @Headers(
        "Authorization: Bearer SAiECnw8LkfxS9IJmUa_zGMjllgf38pAFGT8AJqsq5He4a0GAil8WDQoyJ_g4uCjGFCFruLARfViymp98svAS3GZme16gJJ9XkItJaqpeUL1L7Vg6lOTK76OtyY7XnYx"
    )
    fun getBusinesses(
        @Query("location") location: String = "Monmouth, OR",
        @Query("limit") results: Int
    ): Call<GetBusinessResultResponse>
}