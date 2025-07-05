package com.example.atmfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.atmfinder.databinding.ActivityAtmDetailBinding

class AtmDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAtmDetailBinding
    private val dbHelper = MongoDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val atmId = intent.getStringExtra("ATM_ID")
        val atm = dbHelper.getAtmById(atmId)

        atm?.let {
            binding.tvBank.text = it.bankType
            binding.tvLocation.text = it.locationName
            binding.tvAddress.text = it.address
            binding.tvMoneyStatus.text = it.moneyStatus
            binding.tvTrafficStatus.text = it.trafficStatus
            Glide.with(this).load(it.photoUrl).into(binding.ivAtmPhoto)
        }
    }
}