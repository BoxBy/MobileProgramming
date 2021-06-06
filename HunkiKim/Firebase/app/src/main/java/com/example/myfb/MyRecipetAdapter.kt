package com.example.myfb
import android.graphics.Bitmap
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.myfb.databinding.RowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class MyRecipetAdapter(options: FirebaseRecyclerOptions<Recipe>)
    :FirebaseRecyclerAdapter<Recipe, MyRecipetAdapter.ViewHolder>(options)
{
    lateinit var editText: EditText

    interface OnItemClickListener {
        fun OnItemCLick(view: View, position: Int)
    }
    var itemClickListener: OnItemClickListener? = null

        inner class ViewHolder(val binding: RowBinding):RecyclerView.ViewHolder(binding.root){
            init {
                binding.root.setOnClickListener {
                    itemClickListener!!.OnItemCLick(it,adapterPosition)
                }
            }
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Recipe) {
holder.binding.apply {


    name.text = model.recipe_num.toString()
    time.text = model.name
    imageV.setImageResource(R.drawable.img1)

}
    }


}