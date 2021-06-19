package com.example.myrecipe.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myrecipe.R
import com.example.myrecipe.RecipeData
import com.example.myrecipe.databinding.ActivityReciepeBinding
import com.example.myrecipe.ui.FbRecipeAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.concurrent.timer

class reciepeActivity : AppCompatActivity() {
   lateinit var binding:ActivityReciepeBinding
    lateinit var adapter: FbRecipeAdapter
    var time=0
    lateinit var timerTask: Timer
    lateinit var rdb:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityReciepeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = intent

        val name = intent.getStringExtra("name")
        val cooking = intent.getStringExtra("cooking")
        val ingredient = intent.getStringExtra("ingredient")
        val img = intent.getStringExtra("img")
        val time = intent.getStringExtra("time")


        binding.apply{
            Glide.with(this@reciepeActivity).load(img).into(recipeimg)
            cooktime.text=time
            recipe.text=name
            cookingre.text = ingredient
            cookcontent.text=cooking
        }

   Timerinit()

    }
    fun start()
    {   //var sec=300/60  //5분설정
        // var milli=300%60
        val alpa=binding.minit.text.toString()
        val beta=binding.sec.text.toString()

        var sec=Integer.parseInt(alpa)

        var milli=(Integer.parseInt(beta))%60


        timerTask= timer(period = 1000,initialDelay = 1000)
        {
            time++
            //수행할동작
            runOnUiThread {

                binding.minit.setText(sec.toString())
                if(milli.toInt()<10)
                {
                    binding.sec.setText("0"+milli.toString())
                }
                else
                binding.sec.setText(milli.toString())
                //   binding.textView.text="${num-milli}"
            }
            if(sec==0 &&milli==0)
            {
                timerTask.cancel()
                binding.minit.setText("0")
                binding.sec.setText("00")
            }
            if(milli==0)
            {
                sec--
                milli=60

            }

            milli--
        }
    }

    fun Timerinit()
    {
        binding.button124214.setOnClickListener {
            timerTask.cancel()
        }
        binding.start.setOnClickListener {
            start()
        }

    }



    }
