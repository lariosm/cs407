package com.example.listit

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

const val LISTING_BACKDROP = "extra_listing_backdrop"
const val LISTING_NAME = "extra_listing_name"
const val LISTING_PRICE = "extra_listing_price"
const val LISTING_DESCRIPTION = "extra_listing_description"
const val LISTING_LOCATION = "extra_listing_location"

class ListingDetailsActivity : AppCompatActivity() {
    private lateinit var backdrop: ImageView
    private lateinit var listingName: TextView
    private lateinit var listingPrice: TextView
    private lateinit var listingDescription: TextView
    private lateinit var listingLocation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_details)

        backdrop = findViewById(R.id.details_listing_backdrop)
        listingName = findViewById(R.id.details_listing_name)
        listingPrice = findViewById(R.id.details_listing_price)
        listingDescription = findViewById(R.id.details_listing_description)
        listingLocation = findViewById(R.id.details_listing_location)

        val extras = intent.extras

        if(extras != null) {
            listingDetails(extras)
        }
        else {
            finish()
        }
    }

    private fun listingDetails(extras: Bundle) {
        extras.getString(LISTING_BACKDROP)?.let { imageURL ->
            Glide.with(this)
                .load(imageURL)
                .transform(CenterCrop())
                .into(backdrop)
        }

        listingName.text = extras.getString(LISTING_NAME, "")
        listingPrice.text = extras.getString(LISTING_PRICE, "")
        listingDescription.text = extras.getString(LISTING_DESCRIPTION, "")
        listingLocation.text = extras.getString(LISTING_LOCATION, "")
    }
}