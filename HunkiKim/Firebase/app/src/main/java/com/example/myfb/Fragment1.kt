package com.example.myfb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfb.databinding.ActivityMainBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Fragment1 : Fragment() {
    var nameList:ArrayList<String> = ArrayList()
    lateinit var recyclerView:RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var rdb: DatabaseReference
    lateinit var adapter: MyRecipetAdapter
    lateinit var editText:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        System.out.println("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        System.out.println("onCreateView")
        var view:View?=null
        view = inflater.inflate(R.layout.fragment_1, container, false)
            editText = view.findViewById(R.id.edittext)
            layoutManager = LinearLayoutManager(this.context , LinearLayoutManager.VERTICAL, false)
            rdb = FirebaseDatabase.getInstance("https://recipe-96ca4-default-rtdb.firebaseio.com/").getReference("Recipe")
            val query = rdb.limitToLast(100)
            val option = FirebaseRecyclerOptions.Builder<Recipe>()
                    .setQuery(query, Recipe::class.java)
                    .build()
            adapter = MyRecipetAdapter(option)
            recyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            adapter.startListening()
            editText.addTextChangedListener {
            val pname =it.toString()
        }
        // Inflate the layout for this fragment

        return view
    }
    fun search(serachWord : String, option : String) {

    }
    private fun init() {
//        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        rdb = FirebaseDatabase.getInstance("https://recipe-96ca4-default-rtdb.firebaseio.com/")
//            .getReference("Recipe")
//        System.out.println(rdb);
//        val query = rdb.limitToLast(100)
//        val option = FirebaseRecyclerOptions.Builder<Recipe>()
//            .setQuery(query, Recipe::class.java)
//            .build()
//
//        adapter = MyRecipetAdapter(option)
//        adapter.itemClickListener = object : MyRecipetAdapter.OnItemClickListener {
//            override fun OnItemCLick(view: View, position: Int) {
//
//            }
//
//        }

    }
}