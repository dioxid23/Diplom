package com.example.diplom

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplom.databinding.ActivityListCardBinding

class ListCardActivity : AppCompatActivity() {

    private var item = TypeAnimal()
    lateinit var binding: ActivityListCardBinding

    private val adapter = ItemCardAnimalAdapter()
    private val cardsAnimals = arrayListOf(
        ItemCardAnimal(
            R.drawable.mouse_card,
            "Мышь",
            "Насекомоядные"
        ),
        ItemCardAnimal(
            R.drawable.hare_card,
            "Заяц",
            "Зайцеобразные"
        ),
        ItemCardAnimal(
            R.drawable.hedgehog_card,
            "Ежик",
            "Насекомоядные"
        ),
        ItemCardAnimal(
            R.drawable.lion_card,
            "Лев",
            "Хищные"
        ),
        ItemCardAnimal(
            R.drawable.wolf_card,
            "Волк",
            "Хищные"
        ),
        ItemCardAnimal(
            R.drawable.fox_card,
            "Лиса",
            "Хищные"
        ),
        ItemCardAnimal(
            R.drawable.bear_card,
            "Медведь",
            "Хищные"
        ),
        ItemCardAnimal(
            R.drawable.dog_card,
            "Песель",
            "Домашние заеньки"
        ),
        ItemCardAnimal(
            R.drawable.cat_card,
            "Котенька",
            "Домашние заеньки"
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        val arguments = intent.extras
        if (arguments != null) {
            item = arguments.getSerializable("item") as TypeAnimal
        }
        binding.apply {
            textViewTitleMammals.text = item.title
        }
    }

    private fun init() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@ListCardActivity)
            recyclerView.adapter = adapter
        }
        adapter.addItemCardAnimal(cardsAnimals)
    }
}