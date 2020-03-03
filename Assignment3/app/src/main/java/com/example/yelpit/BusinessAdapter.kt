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
    private val onBusinessClick: (businessResult: BusinessResult) -> Unit
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
            toggleButton.setOnClickListener {
                val user = FirebaseAuth.getInstance().currentUser
                val lIkeBusiness = LikeBusiness(user!!.uid, business.id)
                val database = FirebaseDatabase.getInstance()
                val newLikeReference = database.reference.child("Users").push().key
                //val newLike = lIkeBusiness.copy(newLikeReference.key.toString())
                database.reference.child("Users").child(newLikeReference.toString()).setValue(lIkeBusiness)


            }

            val user = FirebaseAuth.getInstance().currentUser
            var mySnapshot : ArrayList<LikeBusiness>? = ArrayList<LikeBusiness>()
            val database = FirebaseDatabase.getInstance()
            database.reference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (like in snapshot.children) {
                        mySnapshot?.add(snapshot.getValue(LikeBusiness::class.java)!!)
                    }
                }

            })

            var liked = false
            mySnapshot?.forEach {
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