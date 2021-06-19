package com.example.myrecipe.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipe.R
import com.example.myrecipe.databinding.RowIngredientBinding

class IgRecipeAdapter(val items:ArrayList<String>):
    RecyclerView.Adapter<IgRecipeAdapter.ViewHolder>()
{
    companion object{
        var checkboxList = arrayListOf<checkboxData>()
    }
    var IgList:ArrayList<String> = ArrayList()
    inner class ViewHolder(val binding: RowIngredientBinding): RecyclerView.ViewHolder(binding.root){
        var checkbox: CheckBox = itemView!!.findViewById(R.id.checkBox)
        var ig: TextView = itemView!!.findViewById<TextView>(R.id.igtext)
        fun bind(data : String, num: Int){
            binding.apply {
                igtext.text = items[num]
                if(num>= checkboxList.size)
                    checkboxList.add(num, checkboxData(num,false))

                checkBox.isChecked = checkboxList[num].checked

                checkBox.setOnClickListener {
                    checkboxList[num].checked = checkbox.isChecked
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IgRecipeAdapter.ViewHolder {
        val view = RowIngredientBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: IgRecipeAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun getCheckBox(): ArrayList<checkboxData>{
        return checkboxList
    }

}