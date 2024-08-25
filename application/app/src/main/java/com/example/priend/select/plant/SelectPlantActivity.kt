package com.example.priend.select.plant

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.priend.R
import com.example.priend.databinding.ActivityMainBinding
import com.example.priend.databinding.ActivitySelectPlantBinding

class SelectPlantActivity : AppCompatActivity() {

    private var _binding : ActivitySelectPlantBinding? = null

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySelectPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}