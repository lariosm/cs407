package com.example.yelpit

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object BusinessesRepository {
    private val api: Api
    init {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.yelp.com/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(Api::class.java)
    }

    fun getBusinessResults(
        offset: Int = 1,
        onSuccess: (businessResults: List<BusinessResult>) -> Unit,
        onError: () -> Unit
    ) {
        api.getBusinessResults(offset = offset)
            .enqueue(object : Callback<GetBusinessResultResponse> {
                override fun onResponse(
                    call: Call<GetBusinessResultResponse>,
                    response: Response<GetBusinessResultResponse>
                ) {
                    if(response.isSuccessful) {
                        val responseBody = response.body()

                        if(responseBody != null) {
                            onSuccess.invoke(responseBody.businesses)
                        }
                        else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetBusinessResultResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}