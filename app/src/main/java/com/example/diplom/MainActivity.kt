package com.example.diplom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplom.databinding.ActivityMainBinding
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val adapter = TypeAnimalAdapter()
    private val typesAnimals = arrayListOf(
        TypeAnimal(
            R.drawable.insects_img,
            "Насекомые",
            "Хордовые"
        ),
        TypeAnimal(
            R.drawable.mammals_img,
            "Млекопитающие",
            "Хордовые"
        ),
        TypeAnimal(
            R.drawable.birds_img,
            "Птицы",
            "Хордовые"
        ),
        TypeAnimal(
            R.drawable.reptile_img,
            "Рептилии",
            "Хордовые"
        ),
        TypeAnimal(
            R.drawable.frog_img,
            "Земноводные",
            "Хордовые"
        ),
        TypeAnimal(
            R.drawable.fish_img,
            "Рыбы",
            "Хордовые"
        ),
        TypeAnimal(
            R.drawable.crab_img,
            "Ракообразные",
            "Хордовые"
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.adapter = adapter
        }
        adapter.addTypeAnimal(typesAnimals)
    }
}