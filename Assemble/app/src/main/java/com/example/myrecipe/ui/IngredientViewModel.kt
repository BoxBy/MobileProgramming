package com.example.myrecipe.ui

import android.util.Log
import androidx.lifecycle.ViewModel

class IngredientViewModel : ViewModel() {
    var cList: ArrayList<checkboxData> = ArrayList()
    var IgList: ArrayList<String> = ArrayList()

    fun setCheck(data : ArrayList<String>){
        IgList = data
        Log.i("i","setdata")
    }

    fun getCheck() : ArrayList<String>{
        Log.i("i","getdata")
        return IgList
    }
}