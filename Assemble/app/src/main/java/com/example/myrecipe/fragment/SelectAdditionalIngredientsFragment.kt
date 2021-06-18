package com.example.myrecipe.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myrecipe.R
import com.example.myrecipe.activity.MarketSearchActivity

class SelectAdditionalIngredientsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_additional_ingredients, container, false)
        var text = view.findViewById<TextView>(R.id.textViewMarket)
        text.setOnClickListener {
            val intent = Intent(context,MarketSearchActivity::class.java)
            startActivity(intent)
        }
        return view
    }

}