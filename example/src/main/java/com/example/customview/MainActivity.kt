package com.example.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.customview.databinding.ActivityMainBinding
import com.iamkamrul.utils.Shape

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Date
        
        binding.btn1.setOnClickListener {
            binding.btn4.setShape(
                backgroundColor = ContextCompat.getColor(applicationContext,R.color.teal_200),
                backgroundShapeType = Shape.Oval
            )

        }
    }
}