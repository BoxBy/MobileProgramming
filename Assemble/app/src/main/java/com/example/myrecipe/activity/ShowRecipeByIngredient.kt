package com.example.myrecipe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipe.RecipeData
import com.example.myrecipe.databinding.ActivityShowRecipeByIngredientBinding
import com.example.myrecipe.ui.FbRecipeAdapter
import com.example.myrecipe.ui.IgRecipeAdapter
import com.example.myrecipe.ui.SeRecipeAdapter
import com.example.myrecipe.ui.checkboxData
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class ShowRecipeByIngredient : AppCompatActivity() {
    lateinit var binding: ActivityShowRecipeByIngredientBinding
    lateinit var rdb: DatabaseReference
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: FbRecipeAdapter
    lateinit var sadapter: SeRecipeAdapter
    var rList: ArrayList<RecipeData> = ArrayList()
    var igList: ArrayList<String> = ArrayList()
    var cList: ArrayList<checkboxData> = ArrayList()
    var numList:ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowRecipeByIngredientBinding.inflate(layoutInflater)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val intent = intent;
        igList = intent.getStringArrayListExtra("ing") as ArrayList<String>
        cList = intent.getSerializableExtra("recipe") as ArrayList<checkboxData>
        Log.i("i",igList.toString())
        Log.i("i",cList.toString())
        init()
        setContentView(binding.root)
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
                        var rrList: ArrayList<RecipeData> = rList.clone() as ArrayList<RecipeData>
                        var count = 0
                        var flag = false
                        for (j in 0..rrList.size - 1) {

                            for (i in numList) {
                                if (rrList[count].ingredient.contains(igList[i])) {

                                } else {
                                    rrList.remove(rrList[count])
                                    flag = true
                                    break
                                }
                            }
                            if (flag == true) {
                                flag = false
                                continue
                            }
                            count++
                        }
                        sadapter = SeRecipeAdapter(rrList)
                        binding.recyclerViewIngRecipe.layoutManager = layoutManager
                        binding.recyclerViewIngRecipe.adapter = sadapter
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        rdb.addValueEventListener(postLister)
        rdb.get()
        adapter = FbRecipeAdapter(option)

        numList.clear()
        for (i in cList) {
            if (i.checked)
                numList.add(i.id)
        }
        Log.i("t",rList.toString())

    }
}