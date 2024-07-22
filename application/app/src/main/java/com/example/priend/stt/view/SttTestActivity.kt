package com.example.priend.stt.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.priend.R
import com.example.priend.databinding.ActivitySttTestBinding

class SttTestActivity : AppCompatActivity() {

    private var _binding : ActivitySttTestBinding? = null

    private val binding : ActivitySttTestBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySttTestBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}