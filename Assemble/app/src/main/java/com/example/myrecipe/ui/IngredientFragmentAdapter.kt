package com.example.myrecipe.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myrecipe.fragment.SelectAdditionalIngredientsFragment
import com.example.myrecipe.fragment.SelectIngredientsFragment

class IngredientFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> SelectIngredientsFragment()
            1 -> SelectAdditionalIngredientsFragment()
            else -> SelectIngredientsFragment()
        }

        return fragment
    }
}