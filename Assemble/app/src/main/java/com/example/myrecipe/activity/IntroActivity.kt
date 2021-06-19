package com.example.myrecipe.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipe.databinding.ActivityIntroBinding

// 로딩화면
// 개요 : 로딩화면으로 로고를 보여줌
// 기능 : db정보를 받아오는 등의 작업을 진행한다

class IntroActivity : AppCompatActivity() {
    lateinit var binding:ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityIntroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    fun init(){
        with(binding){
            BtnAddRecipeActivity.setOnClickListener {
                val intent = Intent(this@IntroActivity, AddRecipeActivity::class.java)
                startActivity(intent)
            }
            BtnSearchActivity.setOnClickListener {
                val intent = Intent(this@IntroActivity, SearchRecipeActivity::class.java)
                startActivity(intent)
            }
            BtnSelectActivity.setOnClickListener {
                val intent = Intent(this@IntroActivity, SelectIngredientsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}