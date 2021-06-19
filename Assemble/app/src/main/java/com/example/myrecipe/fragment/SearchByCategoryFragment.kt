package com.example.myrecipe.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipe.R
import com.example.myrecipe.RecipeData
import com.example.myrecipe.ui.FbRecipeAdapter
import com.example.myrecipe.ui.SeRecipeAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class SearchByCategoryFragment : Fragment() {
    var rList:ArrayList<RecipeData> = ArrayList()
    var cList:ArrayList<RecipeData> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var rdb: DatabaseReference
    lateinit var adapter: FbRecipeAdapter
    lateinit var sadapter: SeRecipeAdapter
    lateinit var ebtn : RadioButton
    lateinit var mbtn : RadioButton
    lateinit var hbtn : RadioButton
    lateinit var tenbtn : RadioButton
    lateinit var thbtn : RadioButton
    lateinit var sibtn : RadioButton
    lateinit var rgroup1: RadioGroup
    lateinit var rgroup2: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View?=null
        view = inflater.inflate(R.layout.fragment_search_by_category, container,false)
        ebtn = view.findViewById(R.id.eazy)
        mbtn = view.findViewById(R.id.mid)
        hbtn = view.findViewById(R.id.hard)
        tenbtn = view.findViewById(R.id.time10)
        thbtn = view.findViewById(R.id.time30)
        sibtn = view.findViewById(R.id.time30a)
        rgroup1 = view.findViewById(R.id.radiogroup1)
        rgroup2 = view.findViewById(R.id.radiogroup2)

        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
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
        recyclerView = view.findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.startListening()

        rgroup1.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.time10->{
                    adapter.stopListening()
                    cList.clear()
                    var num = 0
                    if(rgroup2.checkedRadioButtonId==-1){
                        for(i in 0 until rList.size){
                            num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                            if(num<=10){
                                cList.add(rList[i])
                            }
                        }
                        sadapter = SeRecipeAdapter(cList)
                        recyclerView.adapter =sadapter
                    }
                    else{
                        if(ebtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if((rList[i].difficulty.contains("아무나") || rList[i].difficulty.contains("초급")) && num<=10){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else if(mbtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if(rList[i].difficulty.contains("중급") && num<=10){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else{
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if(rList[i].difficulty.contains("고급") && num<=10){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                    }
                }
                R.id.time30->{
                    adapter.stopListening()
                    cList.clear()
                    var num = 0
                    if(rgroup2.checkedRadioButtonId==-1){
                        for(i in 0 until rList.size){
                            num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                            if(num<=30){
                                cList.add(rList[i])
                            }
                        }
                        sadapter = SeRecipeAdapter(cList)
                        recyclerView.adapter =sadapter
                    }
                    else{
                        if(ebtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if((rList[i].difficulty.contains("아무나") || rList[i].difficulty.contains("초급")) && num<=30){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else if(mbtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if(rList[i].difficulty.contains("중급") && num<=30){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else{
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if(rList[i].difficulty.contains("고급") && num<=30){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                    }
                }
                R.id.time30a->{
                    adapter.stopListening()
                    cList.clear()
                    var num = 0
                    if(rgroup2.checkedRadioButtonId==-1){
                        for(i in 0 until rList.size){
                            num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                            if(rList[i].cooking_time[1].toInt()<48 || rList[i].cooking_time[1].toInt()>57)
                                continue
                            if(num>30){
                                cList.add(rList[i])
                            }
                        }
                        sadapter = SeRecipeAdapter(cList)
                        recyclerView.adapter =sadapter
                    }else{
                        if(ebtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if(rList[i].cooking_time[1].toInt()<48 || rList[i].cooking_time[1].toInt()>57)
                                    continue
                                if((rList[i].difficulty.contains("아무나") || rList[i].difficulty.contains("초급")) && num>30){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else if(mbtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if(rList[i].cooking_time[1].toInt()<48 || rList[i].cooking_time[1].toInt()>57)
                                    continue
                                if(rList[i].difficulty.contains("중급") && num>30){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else{
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if(rList[i].cooking_time[1].toInt()<48 || rList[i].cooking_time[1].toInt()>57)
                                    continue
                                if(rList[i].difficulty.contains("고급") && num>30 ){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                    }

                }
            }
        }
        rgroup2.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.eazy->{
                    adapter.stopListening() // 기존 어댑터 멈춤
                    cList.clear()
                    if(rgroup1.checkedRadioButtonId == -1){
                        if(rList.size>0){
                            for(i in 0 until rList.size){
                                if(rList[i].difficulty.contains("아무나") || rList[i].difficulty.contains("초급")){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }

                    }else{
                        var num = 0
                        if(tenbtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if((rList[i].difficulty.contains("아무나") || rList[i].difficulty.contains("초급")) && num<=10){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else if(thbtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if((rList[i].difficulty.contains("아무나") || rList[i].difficulty.contains("초급")) && num<=30){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else{
                            if(rList.size>0){
                                for(i in 0 until rList.size){
                                    num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                    if(rList[i].cooking_time[1].toInt()<48 || rList[i].cooking_time[1].toInt()>57)
                                        continue
                                    if((rList[i].difficulty.contains("아무나") || rList[i].difficulty.contains("초급")) && num>30){
                                        cList.add(rList[i])
                                    }
                                }
                                sadapter = SeRecipeAdapter(cList)
                                recyclerView.adapter =sadapter
                            }
                        }
                    }
                }
                R.id.mid->{
                    adapter.stopListening()
                    cList.clear()
                    if(rgroup1.checkedRadioButtonId == -1){
                        adapter.stopListening() // 기존 어댑터 멈춤
                        cList.clear()
                        if(rList.size>0){
                            for(i in 0 until rList.size){
                                if(rList[i].difficulty.contains("중급")){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                    }
                    else{
                        var num = 0
                        if(tenbtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if(rList[i].difficulty.contains("중급") && num<=10){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else if(thbtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if(rList[i].difficulty.contains("중급") && num<=30){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else{
                            if(rList.size>0){
                                for(i in 0 until rList.size){
                                    num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                    if(rList[i].cooking_time[1].toInt()<48 || rList[i].cooking_time[1].toInt()>57)
                                        continue
                                    if(rList[i].difficulty.contains("중급") && num>30){
                                        cList.add(rList[i])
                                    }
                                }
                                sadapter = SeRecipeAdapter(cList)
                                recyclerView.adapter =sadapter
                            }
                        }
                    }
                }
                R.id.hard->{
                    adapter.stopListening()
                    cList.clear()
                    if(rgroup1.checkedRadioButtonId == -1){
                        adapter.stopListening() // 기존 어댑터 멈춤
                        cList.clear()
                        if(rList.size>0){
                            for(i in 0 until rList.size){
                                if(rList[i].difficulty.contains("고급")){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                    }
                    else{
                        var num = 0
                        if(tenbtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if(rList[i].difficulty.contains("고급") && num<=10){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else if(thbtn.isChecked){
                            for(i in 0 until rList.size){
                                num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                if(rList[i].difficulty.contains("고급") && num<=30){
                                    cList.add(rList[i])
                                }
                            }
                            sadapter = SeRecipeAdapter(cList)
                            recyclerView.adapter =sadapter
                        }
                        else{
                            if(rList.size>0){
                                for(i in 0 until rList.size){
                                    num = (rList[i].cooking_time[0].toInt() - 48)*10 + (rList[i].cooking_time[1].toInt() - 48)
                                    if(rList[i].cooking_time[1].toInt()<48 || rList[i].cooking_time[1].toInt()>57)
                                        continue
                                    if(rList[i].difficulty.contains("고급") && num>30){
                                        cList.add(rList[i])
                                    }
                                }
                                sadapter = SeRecipeAdapter(cList)
                                recyclerView.adapter =sadapter
                            }
                        }
                    }
                }
            }
        }
        return view
    }
}
