package com.example.yelpit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var businessResults: RecyclerView
    private lateinit var businessResultsAdapter: BusinessAdapter
    private lateinit var businessResultsLayoutMgr: LinearLayoutManager

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
        businessResultsAdapter = BusinessAdapter(mutableListOf()) { business -> showBusinessDetails(business)}
        businessResults.adapter = businessResultsAdapter

        BusinessesRepository.getBusinessResults(
            businessResultsOffset,
            onSuccess = ::onBusinessesFetched,
            onError = ::onError
        )

        getBusinessResults()
    }

    private fun onBusinessesFetched(businesses: List<BusinessResult>) {
        Log.d("MainActivity:", "$businesses")
        businessResultsAdapter.appendBusinesses(businesses)
        attachBusinessResultsOnScrollListener()
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
                    //Disable scroll listener, increment businessResultsLimit and call function
                    businessResults.removeOnScrollListener(this)
                    businessResultsOffset += 20
                    getBusinessResults()
                }
            }
        })
    }

    private fun showBusinessDetails(business: BusinessResult) {
        val intent = Intent(this, BusinessDetailsActivity::class.java)
        intent.putExtra(BUSINESS_BACKDROP, business.imageURL)
        intent.putExtra(BUSINESS_NAME, business.name)
        intent.putExtra(BUSINESS_RATING, business.rating)
        intent.putExtra(BUSINESS_REVIEWCOUNT, "${business.reviewCount} reviews")
        intent.putExtra(BUSINESS_PHONE, "Phone: ${business.phone}")
        intent.putExtra(BUSINESS_CITY, "${business.location.city},")
        intent.putExtra(BUSINESS_COUNTRY, business.location.country)
        intent.putExtra(BUSINESS_STATE, business.location.state)
        intent.putExtra(BUSINESS_ADDRESS, business.location.address)
        intent.putExtra(BUSINESS_ZIPCODE, business.location.zipCode)
        startActivity(intent)
    }
}
