package com.example.yelpit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class BusinessAdapter(
    private var businesses: List<BusinessResult>
): RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_business, parent, false)
        return BusinessViewHolder(view)
    }

    override fun getItemCount(): Int = businesses.size

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        holder.bind(businesses[position])
    }

    fun updateBusinesses(businesses: List<BusinessResult>) {
        this.businesses = businesses
        notifyDataSetChanged()
    }

    inner class BusinessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.item_business_photo)

        fun bind(business: BusinessResult) {
            val origImageURL = business.imageURL
            val scaledImageURL = origImageURL.replace("o.jpg", "l.jpg")

            Glide.with(itemView)
                .load(scaledImageURL)
                .transform(CenterCrop())
                .into(poster)
        }
    }
}