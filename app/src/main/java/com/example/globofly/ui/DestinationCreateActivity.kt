package com.example.globofly.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.globofly.R
import com.example.globofly.databinding.ActivityDestinyCreateBinding
import com.example.globofly.models.Destination
import com.example.globofly.network.ApiClient
import com.example.globofly.network.DestinationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {
    private val TAG = javaClass.name
    private lateinit var binding: ActivityDestinyCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_destiny_create)

        setSupportActionBar(binding.toolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnAdd.setOnClickListener {
            val newDestination = Destination()
            newDestination.city = binding.etCity.text.toString()
            newDestination.description = binding.etDescription.text.toString()
            newDestination.country = binding.etCountry.text.toString()

            val service = ApiClient.buildService(DestinationService::class.java)
            val requestCall = service.addDestination(newDestination)
            requestCall.enqueue(object : Callback<Destination> {
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@DestinationCreateActivity,
                            "New Destination Added",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            this@DestinationCreateActivity,
                            "Error Adding Destination",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })

            // To be replaced by retrofit code
//            SampleData.addDestination(newDestination)
            finish() // Move back to DestinationListActivity
        }
    }
}