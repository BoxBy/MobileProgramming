package com.example.myrecipe.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.myrecipe.R
import com.example.myrecipe.RecipeData
import com.example.myrecipe.activity.MarketSearchActivity
import com.example.myrecipe.activity.SelectIngredientsActivity
import com.example.myrecipe.databinding.RowIngredientBinding
import com.example.myrecipe.ui.IgRecipeAdapter
import com.example.myrecipe.ui.IngredientViewModel
import com.example.myrecipe.ui.checkboxData

class SelectAdditionalIngredientsFragment : Fragment() {
    lateinit var layoutManager: LinearLayoutManager
    lateinit var viewModel: IngredientViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var iadapter: IgRecipeAdapter
    lateinit var textView: TextView
    var cList: ArrayList<checkboxData> = ArrayList()
    var igList: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onResume() {
        super.onResume()
        igList = viewModel.IgList
        cList = viewModel.cList
        var count = 0
        for(temp in cList){
            if(temp.checked)
                count++
        }
        iadapter = IgRecipeAdapter(igList)
        iadapter.itemClickListener = object :IgRecipeAdapter.OnItemClickListener{
            override fun OnitemClick(
                holder: RowIngredientBinding,
                view: View,
                data: String,
                position: Int
            ) {
                val temp2 = iadapter.getChecked() - count
                textView.text = temp2.toString() + "개 선택됨"
            }

        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = iadapter
    }

    override fun onPause() {
        super.onPause()
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_additional_ingredients, container, false)
        var text = view.findViewById<TextView>(R.id.textViewMarket)
        textView = view.findViewById(R.id.textViewTotal)
        recyclerView = view.findViewById(R.id.recyclerViewExtraIngredient)
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        text.setOnClickListener {
            val intent = Intent(context,MarketSearchActivity::class.java)
            startActivity(intent)
        }
        init()
        return view
    }

    fun init() {
        viewModel = ViewModelProvider(activity as SelectIngredientsActivity)[IngredientViewModel::class.java]
        cList = viewModel.cList
        igList = viewModel.IgList
    }

}

