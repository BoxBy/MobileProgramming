package com.example.myrecipe.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipe.databinding.ActivityMarketInfoBinding

class MarketInfoActivity : AppCompatActivity() {
    lateinit var binding:ActivityMarketInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}