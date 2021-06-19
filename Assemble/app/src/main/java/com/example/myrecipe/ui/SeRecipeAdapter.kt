package com.example.myrecipe.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecipe.R
import com.example.myrecipe.RecipeData
import com.example.myrecipe.activity.reciepeActivity
import com.example.myrecipe.databinding.RowRecipeBinding


class SeRecipeAdapter(val items:ArrayList<RecipeData>):
    RecyclerView.Adapter<SeRecipeAdapter.ViewHolder>()
{
    companion object{

    }
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
        holder.itemView.setOnClickListener {
            var intent = Intent(holder.itemView?.context, reciepeActivity::class.java)
            intent.putExtra("time",items[position].cooking_time)
            intent.putExtra("ingredient",items[position].ingredient)
            intent.putExtra("cooking",items[position].cooking)
            intent.putExtra("name",items[position].name)
            intent.putExtra("img", items[position].img)
            startActivity(holder.itemView.context, intent,null)
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

}