package com.example.myrecipe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myrecipe.R
import com.example.myrecipe.databinding.ActivityAddRecipeBinding

// 레시피 추가
// 개요 : 레시피 추가 버튼을 누를시 나오는 화면이다.
// 레시피에 추가하기 위한 정보들을 입력 할 수 있다.
// 기능 : 레시피 제목을 입력하고 태그 및 요리 방법들을 한단계식 추가 할 수 있다.

class AddRecipeActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}