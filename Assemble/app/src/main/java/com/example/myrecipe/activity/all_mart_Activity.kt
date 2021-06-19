package com.example.myrecipe.activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipe.MartData
import com.example.myrecipe.MartDBHelper
import com.example.myrecipe.databinding.ActivityAllMartBinding
import com.example.myrecipe.ui.all_martAdapter

class all_mart_Activity : AppCompatActivity() {
 lateinit var binding:ActivityAllMartBinding
    var MartList:ArrayList<MartData> =  ArrayList()
    lateinit var myDBhelper: MartDBHelper
    lateinit var adapter:all_martAdapter
    var city_arr= mutableListOf(
        "수원시","고양시","용인시","성남시","부천시","화성시","안산시","남양주시",
        "안양시","평택시","시흥시","파주시","의정부시","김포시","광주시","광명시",
        "군포시","하남시","오산시","양주시","이전시","구리시","안성시","포천시","의왕시",
        "양편군","여주시","동두천시","가평군","과천시","연천군"
    )

    lateinit var adapters: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAllMartBinding.inflate(layoutInflater)
        setContentView(binding.root)

   init()
        initRecyclerView()
        initauto()
    }
    fun initauto()
    {
        var items=""
        adapters= ArrayAdapter(this, R.layout.simple_spinner_dropdown_item,city_arr)
        binding.autoCompleteTextView2.setAdapter(adapters)
        binding.autoCompleteTextView2.setOnItemClickListener { parent, view, position, id ->
            items=parent.getItemAtPosition(position).toString()
        }
        binding.current3.setOnClickListener {
            MartList = myDBhelper.search_cityin_Mart(items)

            adapter = all_martAdapter(MartList)

            binding.recycle.adapter=adapter
        }

    }
    fun init()
    {
        myDBhelper= MartDBHelper(this)
       MartList=myDBhelper.getAllRecord()


    }
    fun initRecyclerView() {



        binding.recycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recycle.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        //리사이클려뷰에서 아이템간격조절하는것!!
        adapter = all_martAdapter(MartList)
        adapter.itemClickListener = object : all_martAdapter.OnItemClickListener {
            override fun OnItemClickListener(
                holer: all_martAdapter.ViewHolder,
                view: View,
                data: MartData,
                position: Int
            ) {
                /*
                val location1=holer.textView2.text.toString()
                val intent= Intent(holer.itemView.context, MarketInfoActivity::class.java)
                intent.putExtra("loc",location1)

                ContextCompat.startActivity(holer.itemView.context,intent,null)
*/
            }
        }
       binding.recycle.adapter= adapter
    }
}