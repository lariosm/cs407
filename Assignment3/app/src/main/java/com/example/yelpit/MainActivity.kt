package com.example.yelpit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var businessResults: RecyclerView
    private lateinit var businessResultsAdapter: BusinessAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        businessResults = findViewById(R.id.list_businesses)
        businessResults.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        businessResultsAdapter = BusinessAdapter(listOf())
        businessResults.adapter = businessResultsAdapter

        BusinessesRepository.getBusinessResults(
            onSuccess = ::onBusinessesFetched,
            onError = ::onError
        )

        BusinessesRepository.getBusiness(
            onSuccess = ::onBusinessFetched,
            onError = ::onError
        )
    }

    private fun onBusinessesFetched(businesses: List<BusinessResult>) {
        businessResultsAdapter.updateBusinesses(businesses)
    }

    private fun onBusinessFetched(business: GetBusinessResponse) {
        Log.d("MainActivity", "Business $business")
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_businesses), Toast.LENGTH_SHORT).show()
    }
}
