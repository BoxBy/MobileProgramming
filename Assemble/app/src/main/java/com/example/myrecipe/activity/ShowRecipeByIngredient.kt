package com.example.myrecipe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myrecipe.databinding.ActivityShowRecipeByIngredientBinding

class ShowRecipeByIngredient : AppCompatActivity() {
    lateinit var binding: ActivityShowRecipeByIngredientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowRecipeByIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}