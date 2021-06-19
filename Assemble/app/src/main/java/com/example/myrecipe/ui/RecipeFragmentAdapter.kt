package com.example.myrecipe.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myrecipe.fragment.SearchByCategoryFragment
import com.example.myrecipe.fragment.SearchByTitleFragment

class RecipeFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> SearchByTitleFragment()
            1 -> SearchByCategoryFragment()
            else -> SearchByTitleFragment()
        }

        return fragment
    }
}