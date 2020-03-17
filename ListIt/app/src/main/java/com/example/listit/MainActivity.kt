package com.example.listit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var listing: RecyclerView
    lateinit var listingAdapter: ListingAdapter
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

        add_listing.setOnClickListener { startActivity(Intent(this, ListingCreate::class.java)) }
    }

    private fun onListingsFetched(listings: List<Listing>) {
        Log.d("MainActivity: ", "$listings")
        listingAdapter.appendListings(listings)
        //save
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_listings), Toast.LENGTH_SHORT).show()
    }

    private fun showListingDetails(listing: Listing) {
        val intent = Intent(this, ListingDetailsActivity::class.java)
        // static backdrop... for now
        intent.putExtra(LISTING_BACKDROP, "http://placeimg.com/640/360/any")
        intent.putExtra(LISTING_NAME, listing.listingTitle)
        intent.putExtra(LISTING_PRICE, "$${listing.askingPrice}")
        intent.putExtra(LISTING_DESCRIPTION, listing.description)
        intent.putExtra(LISTING_LOCATION, "${listing.city}, ${listing.state}")
        startActivity(intent)
    }
}
