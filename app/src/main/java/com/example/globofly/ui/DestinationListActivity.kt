package com.example.globofly.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.globofly.R
import com.example.globofly.utils.DestinationAdapter
import com.example.globofly.databinding.ActivityDestinyListBinding
import com.example.globofly.models.Destination
import com.example.globofly.network.ApiClient
import com.example.globofly.network.DestinationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationListActivity : AppCompatActivity() {
    private val TAG=javaClass.name
    private lateinit var binding: ActivityDestinyListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_destiny_list)
        setSupportActionBar(binding.toolbar)
        binding.fab.setOnClickListener {
            val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadDestination()
    }

    private fun loadDestination() {

        val service = ApiClient.buildService(DestinationService::class.java)
        val requestCall = service.getDestinationList(null)
        requestCall.enqueue(object : Callback<List<Destination>> {
            override fun onResponse(
                call: Call<List<Destination>>,
                response: Response<List<Destination>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()!!
                    binding.recyclerview.adapter = DestinationAdapter(list)

                }
            }

            override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })

    }

}