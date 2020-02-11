package com.example.yelpit

import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("businesses/search")
    @Headers(
        "Authorization: Bearer SAiECnw8LkfxS9IJmUa_zGMjllgf38pAFGT8AJqsq5He4a0GAil8WDQoyJ_g4uCjGFCFruLARfViymp98svAS3GZme16gJJ9XkItJaqpeUL1L7Vg6lOTK76OtyY7XnYx"
    )
    fun getBusinessResults(
        @Query("location") location: String = "Monmouth, OR",
        @Query("limit") results: Int = 20,
        @Query("offset") offset: Int
    ): Call<GetBusinessResultResponse>
}