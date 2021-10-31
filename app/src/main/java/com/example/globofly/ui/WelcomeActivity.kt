package com.example.globofly.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.globofly.R
import com.example.globofly.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        binding.message.text = "Black Friday! Get 50% cash back on saving your first spot."
        binding.button.setOnClickListener {
            getStarted()
        }

    }

    private fun getStarted() {
        val intent = Intent(this, DestinationListActivity::class.java)
        startActivity(intent)
        finish()
    }
}