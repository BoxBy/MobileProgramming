package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timer.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
  lateinit var  binding:ActivityMainBinding
  var time=0
   lateinit var timerTask: Timer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    init()

    }
    fun start()
    {   //var sec=300/60  //5분설정
       // var milli=300%60
        val alpa=binding.minit.text.toString()
        val beta=binding.sec.text.toString()

       var sec=Integer.parseInt(alpa)

       var milli=(Integer.parseInt(beta))%60


     timerTask=timer(period = 1000,initialDelay = 1000)
        {
            time++
            //수행할동작
            runOnUiThread {
                binding.textView2.text=String.format("%02d : %02d",sec,milli)
               binding.minit.setText(sec.toString())
                binding.sec.setText(milli.toString())
             //   binding.textView.text="${num-milli}"
            }
            if(sec==0 &&milli==0)
            {
                timerTask.cancel()
                binding.minit.setText("0")
                binding.sec.setText("0")
            }
            if(milli==0)
            {
                sec--
                milli=60

            }
            milli--
        }
    }
    fun init()
    {
        binding.button.setOnClickListener {
timerTask.cancel()
        }
   binding.button2.setOnClickListener {
        start()
   }
        binding.button3.setOnClickListener {
            time=0
           start()

        }
    }
}