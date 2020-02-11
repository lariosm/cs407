package com.example.yelpit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var businessResults: RecyclerView
    private lateinit var businessResultsAdapter: BusinessAdapter
    private lateinit var businessResultsLayoutMgr: LinearLayoutManager
    private lateinit var businessName : TextView

    private var businessResultsOffset = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        businessResults = findViewById(R.id.list_businesses)
        businessResultsLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        businessResults.layoutManager = businessResultsLayoutMgr
        businessResultsAdapter = BusinessAdapter(mutableListOf())
        businessResults.adapter = businessResultsAdapter

        BusinessesRepository.getBusinessResults(
            businessResultsOffset,
            onSuccess = ::onBusinessesFetched,
            onError = ::onError
        )

        BusinessesRepository.getBusiness(
            onSuccess = ::onBusinessFetched,
            onError = ::onError
        )

        getBusinessResults()
    }

    private fun onBusinessesFetched(businesses: List<BusinessResult>) {
        businessResultsAdapter.appendBusinesses(businesses)
        attachBusinessResultsOnScrollListener()
    }

    private fun onBusinessFetched(business: GetBusinessResponse) {
        Log.d("MainActivity", "Business $business")
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_businesses), Toast.LENGTH_SHORT).show()
    }

    private fun getBusinessResults() {
        BusinessesRepository.getBusinessResults(
            businessResultsOffset,
            ::onBusinessesFetched,
            ::onError
        )
    }

    private fun attachBusinessResultsOnScrollListener() {
        businessResults.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //Total number of businesses inside BusinessAdapter
                val totalItemCount = businessResultsLayoutMgr.itemCount

                //Current number of child views attached to RecyclerView
                val visibleItemCount = businessResultsLayoutMgr.childCount

                //Position of leftmost visible item in list
                val firstVisibleItem = businessResultsLayoutMgr.findFirstVisibleItemPosition()

                if(firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    Log.d("Main: totalItemCount", "$totalItemCount")
                    Log.d("Main: visibleItemCount", "$visibleItemCount")
                    Log.d("Main: firstVisibleItem", "$firstVisibleItem")
                    //Disable scroll listener, increment businessResultsLimit and call function
                    businessResults.removeOnScrollListener(this)
                    businessResultsOffset += 20
                    getBusinessResults()
                }
            }
        })
    }
}
