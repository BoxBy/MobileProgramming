package com.example.myrecipe.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipe.R
import com.example.myrecipe.RecipeData
import com.example.myrecipe.databinding.RowRecipeBinding

// 보류
class ViewRecipeAdapter(val items:ArrayList<RecipeData>):
    RecyclerView.Adapter<ViewRecipeAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: RowRecipeBinding): RecyclerView.ViewHolder(binding.root){

        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewRecipeAdapter.ViewHolder {
        val view = RowRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewRecipeAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            name.text = items[position].name
            time.text = items[position].cooking_time
            if(items[position].difficulty.contains("아무나") ||items[position].difficulty.contains("초급")){
                diff.setImageResource(R.drawable.ez)
            }else if(items[position].difficulty.contains("중급")){
                diff.setImageResource(R.drawable.mi)
            }else{
                diff.setImageResource(R.drawable.hd)
            }
            imageV.setImageResource(R.drawable.img1)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}