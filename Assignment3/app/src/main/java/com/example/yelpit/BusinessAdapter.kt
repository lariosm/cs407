package com.example.yelpit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BusinessAdapter(
    private var businesses: MutableList<BusinessResult>,
    private val onBusinessClick: (businessResult: BusinessResult) -> Unit,
    private val myLikedBusinesses : MutableList<LikeBusiness>
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

    fun appendBusinesses(businesses: List<BusinessResult>) {
        this.businesses.addAll(businesses)
        notifyItemRangeInserted(
            this.businesses.size,
            businesses.size - 1
        )
    }

    inner class BusinessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.item_business_photo)
        private var businessName: TextView = itemView.findViewById(R.id.business_name)
        private var businessRating: RatingBar = itemView.findViewById(R.id.business_rating)
        private var businessReviewCount: TextView = itemView.findViewById(R.id.business_review_count)

        fun bind(business: BusinessResult) {
            val origImageURL = business.imageURL
            val scaledImageURL = origImageURL.replace("o.jpg", "l.jpg")
            val toggleButton = itemView.findViewById<ToggleButton>(R.id.favoriteButton)
            toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->

                val user = FirebaseAuth.getInstance().currentUser
                val lIkeBusiness = LikeBusiness(null, user!!.uid, business.id)
                val database = FirebaseDatabase.getInstance()


                if (isChecked) {

                    var exists = false
                    myLikedBusinesses.forEach {
                        if (it.businessID == business.id) {
                            exists = true
                        }
                    }

                    if (!exists) {
                        val newLikeReference = database.reference.child("Users").push().key
                        lIkeBusiness.key = newLikeReference
                        database.reference.child("Users").child(newLikeReference.toString()).setValue(lIkeBusiness)
                        myLikedBusinesses.add(lIkeBusiness)
                    }


                }
                else {

                    myLikedBusinesses.forEach {
                        if (it.businessID == business.id) {
                            lIkeBusiness.key = it.key.toString()
                            database.reference.child("Users").child(it.key.toString()).removeValue()
                            myLikedBusinesses.remove(lIkeBusiness)

                            myLikedBusinesses.removeIf { liked -> liked.key.equals(it.key.toString()) }
                        }
                    }
                }
            }

            var liked = false
            myLikedBusinesses?.forEach {
                if(business.id == it.businessID) {
                    liked = true
                }
            }

            toggleButton.isChecked = liked

            Glide.with(itemView)
                .load(scaledImageURL)
                .transform(CenterCrop())
                .into(poster)

            businessName.text = business.name
            businessRating.rating = business.rating
            businessReviewCount.text = "${business.reviewCount} reviews"
            itemView.setOnClickListener {onBusinessClick.invoke(business)}
        }
    }
}