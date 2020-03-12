package com.example.listit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class ListingAdapter (
    private var listings: MutableList<Listing>,
    private val onListingClick: (listing: Listing) -> Unit
): RecyclerView.Adapter<ListingAdapter.ListingViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListingViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_listing, parent, false)
        return ListingViewHolder(view)
    }

    override fun getItemCount(): Int = listings.size

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        holder.bind(listings[position])
    }

    fun appendListings(listings: List<Listing>) {
        this.listings.addAll(listings)
        notifyItemRangeInserted(
            this.listings.size,
            listings.size - 1
        )
    }

    inner class ListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.listing_photo)
        private var listingName: TextView = itemView.findViewById(R.id.listing_name)
        private var listingLocation: TextView = itemView.findViewById(R.id.listing_location)
        private var listingPrice: TextView = itemView.findViewById(R.id.listing_price)

        fun bind(listing: Listing) {
            // Image placeholder... for now.
            val imageURL = "https://s3-media2.fl.yelpcdn.com/bphoto/zgjSt_RGjXQMJxYxYSo-bQ/l.jpg"

            Glide.with(itemView)
                .load(imageURL)
                .transform(CenterCrop())
                .into(poster)

            listingName.text = listing.listingTitle
            listingLocation.text = "${listing.city}, ${listing.state}"
            listingPrice.text = "$${listing.askingPrice}"
            itemView.setOnClickListener {onListingClick.invoke(listing)}
        }
    }
}