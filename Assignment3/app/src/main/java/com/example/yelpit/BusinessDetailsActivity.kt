package com.example.yelpit

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

const val BUSINESS_BACKDROP = "extra_business_backdrop"
const val BUSINESS_NAME = "extra_business_name"
const val BUSINESS_RATING = "extra_business_rating"
const val BUSINESS_REVIEWCOUNT = "extra_business_review_count"
const val BUSINESS_PHONE = "extra_business_phone"
const val BUSINESS_CITY = "extra_business_city"
const val BUSINESS_COUNTRY = "extra_business_country"
const val BUSINESS_STATE = "extra_business_state"
const val BUSINESS_ADDRESS = "extra_business_address"
const val BUSINESS_ZIPCODE = "extra_business_zipcode"

class BusinessDetailsActivity : AppCompatActivity() {
    private lateinit var backdrop: ImageView
    private lateinit var name: TextView
    private lateinit var rating: RatingBar
    private lateinit var reviewCount: TextView
    private lateinit var phone: TextView
    private lateinit var city: TextView
    private lateinit var country: TextView
    private lateinit var state: TextView
    private lateinit var address: TextView
    private lateinit var zipCode: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_details)

        backdrop = findViewById(R.id.business_backdrop)
        name = findViewById(R.id.details_business_name)
        rating = findViewById(R.id.details_business_rating)
        reviewCount = findViewById(R.id.details_business_review_count)
        phone = findViewById(R.id.details_business_phone)
        city = findViewById(R.id.details_business_city)
        country = findViewById(R.id.details_business_country)
        state = findViewById(R.id.details_business_state)
        address = findViewById(R.id.details_business_address)
        zipCode = findViewById(R.id.details_business_zipcode)

        val extras = intent.extras

        if(extras != null) {
            businessDetails(extras)
        }
        else {
            finish()
        }
    }

    private fun businessDetails(extras: Bundle) {
        extras.getString(BUSINESS_BACKDROP)?.let { imageURL ->
            Glide.with(this)
                .load(imageURL)
                .transform(CenterCrop())
                .into(backdrop)
        }

        name.text = extras.getString(BUSINESS_NAME, "")
        rating.rating = extras.getFloat(BUSINESS_RATING, 0f)
        reviewCount.text = extras.getString(BUSINESS_REVIEWCOUNT, "")
        phone.text = extras.getString(BUSINESS_PHONE, "")
        city.text = extras.getString(BUSINESS_CITY, "")
        country.text = extras.getString(BUSINESS_COUNTRY, "")
        state.text = extras.getString(BUSINESS_STATE, "")
        address.text = extras.getString(BUSINESS_ADDRESS, "")
        zipCode.text = extras.getString(BUSINESS_ZIPCODE, "")
    }
}
