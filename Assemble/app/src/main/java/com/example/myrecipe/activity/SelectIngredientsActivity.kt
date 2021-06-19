package com.example.myrecipe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myrecipe.R
import com.example.myrecipe.databinding.ActivitySelectIngredientsBinding
import com.example.myrecipe.ui.IngredientFragmentAdapter
import com.example.myrecipe.ui.IngredientViewModel
import com.google.android.material.tabs.TabLayoutMediator

// 남는 재료 선택 – 남는 재료 선택
// 개요 : 남는 재료 선택을 누를시 나오는 화면이다.
// 남는 재료를 선택을 하면 선택된 재료에 맞게 레시피를 보여준다.
// 기능 : 스크롤을 통해 재료를 선택 할 수 있다.

// 남는 재료 선택 – 추가 재료 선택
// 개요 : 남는 재료 화면에서 추가 재료 선택을 누르면 나오는 화면이다.
// 추가 재료를 선택 할 수 있으며 그에 따른 비용이 출력된다.
// 기능 : 남는 재료에서 선택하지 않은 애들만 보여야한다.
// 추가할때마다 비용이 계산되어 보여야한다.


class SelectIngredientsActivity : AppCompatActivity() {
    lateinit var binding:ActivitySelectIngredientsBinding
    lateinit var viewModel: IngredientViewModel
    val textarr = arrayListOf<String>("남는 재료 선택", "추가 재료 선택")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectIngredientsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPager()
    }

    private fun initViewPager() {
        binding.ingredientViewPager.adapter = IngredientFragmentAdapter(this)
        TabLayoutMediator(binding.ingredientTabLayout, binding.ingredientViewPager) { tab, position ->
            tab.text = textarr[position]
        }.attach()
        viewModel = ViewModelProvider(this).get(IngredientViewModel::class.java)
    }
}