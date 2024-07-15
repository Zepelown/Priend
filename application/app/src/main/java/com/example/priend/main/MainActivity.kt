package com.example.priend.main

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.priend.R
import com.example.priend.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
    }


}