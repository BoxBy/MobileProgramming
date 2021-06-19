package com.example.myrecipe

data class RecipeData (var recipe_num:Int, var name:String, var ingredient:String, var img:String,
                       var cooking:String, var cooking_time:String, var people:String, var difficulty:String,
                       var seasoning:String, var tag:String
) {
    constructor():this(0,"noinfo","noinfo","noinfo" , "noinfo"
        ,"noinfo","noinfo","noinfo","noinfo","noinfo")
}