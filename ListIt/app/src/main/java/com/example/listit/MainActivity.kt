package com.example.listit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var listing: RecyclerView
    private lateinit var listingAdapter: ListingAdapter
    private lateinit var listingLayoutMgr: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listing = findViewById(R.id.list_listings)
        listingLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        listing.layoutManager = listingLayoutMgr

        listingAdapter = ListingAdapter(mutableListOf()) {listing -> showListingDetails(listing)}
        listing.adapter = listingAdapter

        ListingsRepository.getListingResults(
            onSuccess = ::onListingsFetched,
            onError = ::onError
        )
    }

    private fun onListingsFetched(listings: List<Listing>) {
        Log.d("MainActivity: ", "$listings")
        listingAdapter.appendListings(listings)
        //save
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_listings), Toast.LENGTH_SHORT).show()
    }

    private fun getListingResults() {
        ListingsRepository.getListingResults(
            ::onListingsFetched,
            ::onError
        )
    }

    /*
    private fun attachBusinessResultsOnScrollListener() {
        listing.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //Total number of businesses inside BusinessAdapter
                val totalItemCount = listingLayoutMgr.itemCount

                //Current number of child views attached to RecyclerView
                val visibleItemCount = listingLayoutMgr.findLastVisibleItemPosition() - listingLayoutMgr.findFirstVisibleItemPosition()

                //Position of leftmost visible item in list
                val firstVisibleItem = businessResultsLayoutMgr.findFirstVisibleItemPosition()


                if(firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    Log.d("Main: visibleItemCount:", "$visibleItemCount")
                    Log.d("Main: totalItemCount:", "$totalItemCount")
                    //Disable scroll listener, increment businessResultsLimit and call function
                    listing.removeOnScrollListener(this)
                    listingOffset += 20
                    getListingResults()
                }
            }
        })
    }
    */

    private fun showListingDetails(listing: Listing) {
        val intent = Intent(this, ListingDetailsActivity::class.java)
        // static backdrop... for now
        intent.putExtra(LISTING_BACKDROP, "https://s3-media2.fl.yelpcdn.com/bphoto/zgjSt_RGjXQMJxYxYSo-bQ/l.jpg")
        intent.putExtra(LISTING_NAME, listing.listingTitle)
        intent.putExtra(LISTING_PRICE, "$${listing.askingPrice}")
        intent.putExtra(LISTING_DESCRIPTION, listing.description)
        intent.putExtra(LISTING_CITY, "${listing.city},")
        intent.putExtra(LISTING_STATE, listing.state)
        intent.putExtra(LISTING_USER, listing.userName)
        startActivity(intent)
    }
}
