package com.example.diplom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.databinding.ItemCardAnimalsBinding

class ItemCardAnimalAdapter: RecyclerView.Adapter<ItemCardAnimalAdapter.ItemCardAnimalViewHolder>() {

    private var itemCardAnimalList = ArrayList<ItemCardAnimal>()
    class ItemCardAnimalViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ItemCardAnimalsBinding.bind(item)
        fun bind(itemCardAnimal: ItemCardAnimal) = with(binding) {
            imageViewCard.setImageResource(itemCardAnimal.imageId)
            textViewTitleCard.text = itemCardAnimal.title
            textViewDescriptionCard.text = itemCardAnimal.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCardAnimalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_animals, parent, false)
        return ItemCardAnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemCardAnimalViewHolder, position: Int) {
        holder.bind(itemCardAnimalList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, InfoAnimalActivity::class.java)
            intent.putExtra("item", itemCardAnimalList[position])
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemCardAnimalList.size
    }

    fun addItemCardAnimal(_itemCardAnimal: ArrayList<ItemCardAnimal>) {
        itemCardAnimalList = _itemCardAnimal
        notifyDataSetChanged()
    }

}