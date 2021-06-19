package com.example.myrecipe.ui
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecipe.R
import com.example.myrecipe.RecipeData
import com.example.myrecipe.databinding.RowRecipeBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class FbRecipeAdapter(options: FirebaseRecyclerOptions<RecipeData>)
    :FirebaseRecyclerAdapter<RecipeData, FbRecipeAdapter.ViewHolder>(options)
{
    lateinit var editText: EditText

    interface OnItemClickListener {
        fun OnItemCLick(view: View, position: Int)
    }
    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(val binding: RowRecipeBinding): RecyclerView.ViewHolder(binding.root){

        init {

            binding.root.setOnClickListener {
                itemClickListener!!.OnItemCLick(it,adapterPosition)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {



        var view = RowRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: RecipeData) {
        holder.binding.apply {


            name.text = model.name
            time.text = model.cooking_time
            if(model.difficulty.contains("아무나") ||model.difficulty.contains("초급")){
                diff.setImageResource(R.drawable.ez)
            }else if(model.difficulty.contains("중급")){
                diff.setImageResource(R.drawable.mi)
            }else{
                diff.setImageResource(R.drawable.hd)
            }

            Glide.with(holder.itemView).load(model.img).into(imageV)

        }
    }



}