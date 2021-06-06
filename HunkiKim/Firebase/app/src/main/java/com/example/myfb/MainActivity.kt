package com.example.myfb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfb.databinding.ActivityMainBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyRecipetAdapter
    lateinit var rdb:DatabaseReference
    var NameList:ArrayList<String> = ArrayList()

    var RecipeList:ArrayList<Recipe> = ArrayList()
    var findQuery = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance("https://recipe-96ca4-default-rtdb.firebaseio.com/").getReference("Recipe")

        val query = rdb.limitToLast(100)
        val option = FirebaseRecyclerOptions.Builder<Recipe>()
            .setQuery(query, Recipe::class.java)
            .build()

        adapter = MyRecipetAdapter(option)

        adapter.itemClickListener = object :MyRecipetAdapter.OnItemClickListener{
            override fun OnItemCLick(view: View, position: Int) {
            }
        }
        binding.apply{


            findbtn.setOnClickListener {


                var fragment1 = Fragment1()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, fragment1)
                transaction.commit()
//
            }
            cfindbtn.setOnClickListener {

            }

        }
    }


    fun find2(){

    }

    fun clearInput(){

    }
    override fun onStart(){
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    fun initAdapter(){
        if(findQuery){
            findQuery = false
            if(adapter!=null)
                adapter.startListening()
            val query = rdb.limitToLast(50)
            val option = FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(query, Recipe::class.java)
                .build()
            adapter = MyRecipetAdapter(option)
            adapter.itemClickListener = object :MyRecipetAdapter.OnItemClickListener{
                override fun OnItemCLick(view: View, position: Int) {
                    binding.apply{

                    }
                }
            }

            adapter.startListening()
        }
    }
}