package com.example.myfb

data class Recipe(var recipe_num:Int, var name:String, var ingredient:String,
var cooking:String, var people:String, var difficulty:String,
                  var seasoning:String, var tag:String
                  ) {
    constructor():this(0,"noinfo","noinfo","noinfo"
    ,"noinfo","noinfo","noinfo","noinfo")
}