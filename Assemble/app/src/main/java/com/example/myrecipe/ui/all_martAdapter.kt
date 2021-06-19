package com.example.myrecipe.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipe.MartData
import com.example.myrecipe.R
import com.example.myrecipe.activity.MarketInfoActivity

class all_martAdapter(val items:ArrayList<MartData>):RecyclerView.Adapter<all_martAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun OnItemClickListener(holer:ViewHolder, view:View, data: MartData, position: Int)
    }

var itemClickListener:OnItemClickListener?=null

    fun moveItem(oldPos:Int,newPos:Int)
    {
        val item=items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos,item)
        notifyItemMoved(oldPos,newPos)
    }
    fun removeItem(pos:Int)
    {
        items.removeAt(pos)
        notifyItemRemoved(pos)

    }


    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView)
    {
        val textView: TextView =itemView.findViewById(R.id.name)
        val textView2:TextView=itemView.findViewById(R.id.location)
    init{

        itemView.setOnClickListener{
            itemClickListener?.OnItemClickListener(this,it,items[adapterPosition],adapterPosition)

        }


    }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //홀더가담고있는 정보들
        holder.textView.text=items[position].name //단어
        holder.textView2.text=items[position].location
    }

    override fun getItemCount(): Int {
      return items.size
    }
}