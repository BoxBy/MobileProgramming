package com.example.myrecipe.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipe.databinding.ActivitySearchRecipeBinding
import com.example.myrecipe.ui.RecipeFragmentAdapter
import com.google.android.material.tabs.TabLayoutMediator

// 레시피 검색 - 이름 검색
// 개요 : 레시피 검색을 누를시 나오는 화면이다.
// 메뉴바를 통해 여러 검색 방법을 선택 할 수 있다.

// 기능 : 검색 창에 이름을 입력 할 시 요리들을 보여준다.
// 요리를 선택 할 시 레시피를 보여준다.
// 제목 비슷한 순으로 정렬

// 레시피 검색 – 태그 검색
// 개요 : 태그 검색을 누를시 나오는 화면이다.
// 기능 : 입력 추천을 통하여 여러 태그들을 보여준다.
// 태그를 하나씩 입력하고 추가 버튼을 누르면 태그를 추가하고 아래 검색 결과가 바뀐다.
// 태그를 선택시 서로 교집합만 보여준다.

class SearchRecipeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchRecipeBinding
    val textarr = arrayListOf<String>("이름 검색", "카테고리 검색")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
    }

    private fun initViewPager() {
        binding.RecipeViewPager.adapter = RecipeFragmentAdapter(this)
        TabLayoutMediator(binding.RecipeTabLayout, binding.RecipeViewPager) { tab, position ->
            tab.text = textarr[position]
        }.attach()
    }
}