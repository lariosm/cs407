package com.example.listit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ListingsRepository {
    private val api: API
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://listitapi.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(API::class.java)
    }

    fun getListingResults(
        // offset: Int = 1,
        onSuccess: (listingResults: List<Listing>) -> Unit,
        onError: () -> Unit
    ) {
        api.getListingResults()
            .enqueue(object : Callback<GetListingResponse> {
                override fun onResponse(
                    call: Call<GetListingResponse>,
                    response: Response<GetListingResponse>
                ) {
                    if(response.isSuccessful) {
                        val responseBody = response.body()

                        if(responseBody != null) {
                            onSuccess.invoke(responseBody.listings)
                        }
                        else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetListingResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}