package com.example.yelpit

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

const val BUSINESS_BACKDROP = "extra_business_backdrop"

class BusinessDetailsActivity : AppCompatActivity() {
    private lateinit var backdrop: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_details)

        backdrop = findViewById(R.id.business_backdrop)

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
    }
}
