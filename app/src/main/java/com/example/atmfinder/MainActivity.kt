package com.example.atmfinder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.atmfinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MongoDBHelper(this) // Initialize MongoDB

        binding.btnCustomer.setOnClickListener {
            startActivity(Intent(this, AtmListActivity::class.java))
        }

        binding.btnProvider.setOnClickListener {
            startActivity(Intent(this, ProviderActivity::class.java))
        }
    }
}