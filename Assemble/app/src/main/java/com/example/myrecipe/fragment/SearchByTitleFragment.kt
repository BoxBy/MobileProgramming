package com.example.myrecipe.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipe.R
import com.example.myrecipe.RecipeData
import com.example.myrecipe.ui.FbRecipeAdapter
import com.example.myrecipe.ui.SeRecipeAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class SearchByTitleFragment : Fragment() {
    var rList:ArrayList<RecipeData> = ArrayList()
    var cList:ArrayList<RecipeData> = ArrayList()
    var nameList:ArrayList<String> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var rdb: DatabaseReference
    lateinit var adapter: FbRecipeAdapter
    lateinit var sadapter: SeRecipeAdapter
    lateinit var editText: EditText
    lateinit var sButton: Button
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
        view = inflater.inflate(R.layout.fragment_search_by_title, container, false)
        editText = view.findViewById(R.id.edittext)
        layoutManager = LinearLayoutManager(this.context , LinearLayoutManager.VERTICAL, false)
        FirebaseApp.initializeApp(requireContext())
        rdb = FirebaseDatabase.getInstance("https://recipe-96ca4-default-rtdb.firebaseio.com/").getReference("Recipe")
        val query = rdb.limitToLast(100)
        val option = FirebaseRecyclerOptions.Builder<RecipeData>()
            .setQuery(query, RecipeData::class.java)
            .build()

        val postLister=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(postsnap in snapshot.children)
                {
                    val apo = postsnap.getValue(RecipeData::class.java)
                    if (apo != null) {
                        rList.add(apo)
                    }
                    //      binding.textView7.text=apo?.word
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        rdb.addValueEventListener(postLister)
        adapter = FbRecipeAdapter(option)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.startListening()
        sButton = view.findViewById(R.id.sbutton)
        sButton.setOnClickListener { // Button 입력시
            adapter.stopListening() // 기존 어댑터 멈춤
            cList.clear()
            if(rList.size>0){
                for(i in 0 until rList.size){
                    if(rList[i].name.contains(editText.text.toString())){
                        cList.add(rList[i])
                    }
                }
                if(editText.text.isNullOrEmpty())
                    sadapter = SeRecipeAdapter(rList)
                else
                    sadapter = SeRecipeAdapter(cList)
                recyclerView.adapter = sadapter
            }
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