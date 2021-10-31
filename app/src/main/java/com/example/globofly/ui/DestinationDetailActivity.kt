package com.example.globofly.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.globofly.R
import com.example.globofly.databinding.ActivityDestinyDetailBinding
import com.example.globofly.models.Destination
import com.example.globofly.network.ApiClient
import com.example.globofly.network.DestinationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationDetailActivity : AppCompatActivity() {
    private val TAG = javaClass.name
    private lateinit var binding: ActivityDestinyDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_destiny_detail)
        setSupportActionBar(binding.toolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(id)

            initUpdateButton(id)

            initDeleteButton(id)
        }
    }

    private fun loadDetails(id: Int) {

        // To be replaced by retrofit code
        /*val destination = SampleData.getDestinationById(id)

        destination?.let {
            binding.etCity.setText(destination.city)
            binding.etDescription.setText(destination.description)
            binding.etCountry.setText(destination.country)

            binding.collapsingToolbar.title = destination.city
        }*/

        val destinationService = ApiClient.buildService(DestinationService::class.java)
        val requestCall = destinationService.getDestination(id)
        requestCall.enqueue(object : Callback<Destination> {
            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                if (response.isSuccessful) {
                    val destination = response.body()!!
                    destination.let {
                        binding.etCity.setText(it.city)
                        binding.etDescription.setText(it.description)
                        binding.etCountry.setText(it.country)
                    }
                } else
                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Failed to retrieve detail",
                        Toast.LENGTH_SHORT
                    ).show()
            }

            override fun onFailure(call: Call<Destination>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }


        })

    }

    private fun initUpdateButton(id: Int) {

        binding.btnUpdate.setOnClickListener {

            val city = binding.etCity.text.toString()
            val description = binding.etDescription.text.toString()
            val country = binding.etCountry.text.toString()

            // To be replaced by retrofit code
            val destination = Destination()
            destination.id = id
            destination.city = city
            destination.description = description
            destination.country = country
            val service = ApiClient.buildService(DestinationService::class.java)
            val requestCall = service.updateDestination(id, city, description, country)
            requestCall.enqueue(object : Callback<Destination> {
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        finish()
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Destination Updated",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Error Updating Destination",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })

            finish()
        }
    }

    private fun initDeleteButton(id: Int) {

        binding.btnDelete.setOnClickListener {

            val service = ApiClient.buildService(DestinationService::class.java)
            val requestCall = service.deleteDestination(id)
            requestCall.enqueue(object : Callback<Destination> {
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        finish()
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Destination Deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Error Deleting Destination",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })
            finish() // Move back to DestinationListActivity
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}