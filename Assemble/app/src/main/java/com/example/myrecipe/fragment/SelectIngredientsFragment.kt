package com.example.myrecipe.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipe.R
import com.example.myrecipe.RecipeData
import com.example.myrecipe.activity.SelectIngredientsActivity
import com.example.myrecipe.ui.*
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*


class SelectIngredientsFragment : Fragment() {
    var rList: ArrayList<RecipeData> = ArrayList()
    var cList: ArrayList<checkboxData> = ArrayList()

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var rdb: DatabaseReference
    lateinit var adapter: FbRecipeAdapter
    lateinit var iadapter: IgRecipeAdapter
    lateinit var viewModel:IngredientViewModel
    var IL: List<String> = listOf()
    var IgList: ArrayList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View? = null
        view = inflater.inflate(R.layout.fragment_select_ingredients, container, false)
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        if (view != null) {
            recyclerView = view.findViewById(R.id.recyclerView3)
        }
        init()
        viewModel = ViewModelProvider(activity as SelectIngredientsActivity)[IngredientViewModel::class.java]
        return view
    }

    override fun onPause() {
        super.onPause()
        viewModel.IgList = IgList
        viewModel.cList = iadapter.getCheckBox()
        viewModel.setCheck(IgList)
    }

    fun init(){
        rdb = FirebaseDatabase.getInstance("https://recipe-96ca4-default-rtdb.firebaseio.com/")
            .getReference("Recipe")
        val query = rdb.limitToLast(100)
        val option = FirebaseRecyclerOptions.Builder<RecipeData>()
            .setQuery(query, RecipeData::class.java)
            .build()
        val postLister = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postsnap in snapshot.children) {
                    val apo = postsnap.getValue(RecipeData::class.java)
                    if (apo != null) {
                        rList.add(apo)
                        changeIgList()
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                    //      binding.textView7.text=apo?.word
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        rdb.addValueEventListener(postLister)
        rdb.get()
        adapter = FbRecipeAdapter(option)

        changeIgList()
        iadapter = IgRecipeAdapter(IgList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = iadapter
//        rbtn.setOnClickListener {
//
//            numList.clear()
//            for (i in cList) {
//                if (i.checked)
//                    numList.add(i.id)
//            }
//            var rrList: ArrayList<RecipeData> = rList.clone() as ArrayList<RecipeData>
//            var count = 0
//            var flag = false
//            for (j in 0..rrList.size - 1) {
//
//                for (i in numList) {
//                    if (rrList[count].ingredient.contains(IgList[i])) {
//
//                    } else {
//                        rrList.remove(rrList[count])
//                        flag = true
//                        break
//                    }
//                }
//                if (flag == true) {
//                    flag = false
//                    continue
//                }
//                count++
//            }
//            sadapter = SeRecipeAdapter(rrList)
//            recyclerView.layoutManager = layoutManager
//            recyclerView.adapter = sadapter
//        }
    }

    fun changeIgList(){
        for (i in rList) {
            IL = i.ingredient.split('\n')
            for (j in IL) {
                if (!IgList.contains(j)) {
                    IgList.add(j)
                }
            }
        }
    }
}