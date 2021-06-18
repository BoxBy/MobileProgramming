package com.example.myrecipe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myrecipe.R
import com.example.myrecipe.databinding.ActivityMarketInfoBinding

class MarketInfoActivity : AppCompatActivity() {
    lateinit var binding:ActivityMarketInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}