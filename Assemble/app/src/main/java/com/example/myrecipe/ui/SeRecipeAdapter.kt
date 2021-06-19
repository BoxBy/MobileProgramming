package com.example.myrecipe.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecipe.R
import com.example.myrecipe.RecipeData
import com.example.myrecipe.databinding.RowRecipeBinding


class SeRecipeAdapter(val items:ArrayList<RecipeData>):
    RecyclerView.Adapter<SeRecipeAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: RowRecipeBinding): RecyclerView.ViewHolder(binding.root){

        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeRecipeAdapter.ViewHolder {
        val view = RowRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeRecipeAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView).load(items[position].img).into(imageV)
            name.text = items[position].name
            time.text = items[position].cooking_time
            if(items[position].difficulty.contains("아무나") ||items[position].difficulty.contains("초급")){
                diff.setImageResource(R.drawable.ez)
            }else if(items[position].difficulty.contains("중급")){
                diff.setImageResource(R.drawable.mi)
            }else{
                diff.setImageResource(R.drawable.hd)
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}