package com.example.myrecipe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myrecipe.R

// 로딩화면
// 개요 : 로딩화면으로 로고를 보여줌
// 기능 : db정보를 받아오는 등의 작업을 진행한다

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }
}