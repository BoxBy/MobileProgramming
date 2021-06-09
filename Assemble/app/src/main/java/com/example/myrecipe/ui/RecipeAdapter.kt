package com.example.myrecipe.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipe.R

class RecipeAdapter(val items:ArrayList<String>)
    : RecyclerView.Adapter<RecipeAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun OnItemClick(holder:RecipeAdapter.ViewHolder, view: View, data:String, position: Int)
    }
    var itemClickListener:OnItemClickListener?=null
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val pname : TextView = itemView.findViewById(R.id.textViewRecipeTitle)
        val ptime : TextView = itemView.findViewById(R.id.textViewRecipeTime)
        init{
            pname.setOnClickListener {
                itemClickListener?.OnItemClick(this,it,items[adapterPosition],adapterPosition)
            }
            ptime.setOnClickListener {
                itemClickListener?.OnItemClick(this,it,items[adapterPosition],adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_recipe,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pname.text = items[position]
        holder.ptime.text = items[position]
    }
    override fun getItemCount(): Int {
        return items.size
    }
}