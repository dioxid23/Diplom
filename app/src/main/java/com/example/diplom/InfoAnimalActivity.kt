package com.example.diplom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.diplom.databinding.ActivityInfoAnimalBinding
import com.example.diplom.databinding.ActivityListCardBinding

class InfoAnimalActivity : AppCompatActivity() {

    private var item = ItemCardAnimal()
    lateinit var binding: ActivityInfoAnimalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val arguments = intent.extras
        if (arguments != null) {
            item = arguments.getSerializable("item") as ItemCardAnimal
        }
        binding.apply {
            imageViewInfo.setImageResource(item.imageId)
            textViewTitleInfo.text = item.title
            textViewDescriptionInfo.text = item.description
            textViewMoreInfo.text = item.moreInfo
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ListCardActivity::class.java)
        startActivity(intent)
        finish()
    }
}