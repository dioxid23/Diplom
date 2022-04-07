package com.example.diplom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.databinding.ItemAnimalTypeBinding

class TypeAnimalAdapter: RecyclerView.Adapter<TypeAnimalAdapter.TypeAnimalViewHolder>() {

    private var typeAnimalList = ArrayList<TypeAnimal>()

    class TypeAnimalViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ItemAnimalTypeBinding.bind(item)
        fun bind(typeAnimal: TypeAnimal) = with(binding){
            imageViewType.setImageResource(typeAnimal.imageId)
            textViewTitleType.text = typeAnimal.title
            textViewDescriptionType.text = typeAnimal.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeAnimalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_animal_type, parent, false)
        return TypeAnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: TypeAnimalViewHolder, position: Int) {
        holder.bind(typeAnimalList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ListCardActivity::class.java)
            intent.putExtra("item", typeAnimalList[position])
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return typeAnimalList.size
    }

    fun addTypeAnimal(_typeAnimal: ArrayList<TypeAnimal>) {
        typeAnimalList = _typeAnimal
        notifyDataSetChanged()
    }
}