package com.example.yelpit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object BusinessesRepository {
    private val api: Api
    init {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.yelp.com/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(Api::class.java)
    }

    fun getBusinesses(results: Int = 10) {
        api.getBusinesses(results = results)
            .enqueue(object : Callback<GetBusinessResultResponse> {
                override fun onResponse(
                    call: Call<GetBusinessResultResponse>,
                    response: retrofit2.Response<GetBusinessResultResponse>
                ) {
                    if(response.isSuccessful) {
                        val responseBody = response.body()

                        if(responseBody != null) {
                            Log.d("Repository", "Businesses: ${responseBody.businesses}")
                        }
                        else {
                            Log.d("Repository", "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<GetBusinessResultResponse>, t: Throwable) {
                    Log.e("Repository", "onfailure", t)
                }
            })
    }
}