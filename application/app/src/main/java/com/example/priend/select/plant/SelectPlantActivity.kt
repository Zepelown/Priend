package com.example.priend.select.plant

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.priend.R
import com.example.priend.databinding.ActivityMainBinding
import com.example.priend.databinding.ActivitySelectPlantBinding
import com.example.priend.main.MainActivity
import com.example.priend.select.plant.view.recyclerview.SelectPlantItem
import com.example.priend.select.plant.view.recyclerview.SelectPlantItemClickListener
import com.example.priend.select.plant.view.recyclerview.SelectPlantViewAdapter

class SelectPlantActivity : AppCompatActivity(), SelectPlantItemClickListener {

    private var _binding : ActivitySelectPlantBinding? = null

    private val binding get() = _binding!!


    val plants = listOf<SelectPlantItem>(
        SelectPlantItem("스파티필룸속",R.drawable.spathiphyllum),
        SelectPlantItem("호야",R.drawable.hoya),
        SelectPlantItem("필로덴드론속",R.drawable.philodendron),
        SelectPlantItem("산세비에리아",R.drawable.sansevieria),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySelectPlantBinding.inflate(layoutInflater)

        val adapter = SelectPlantViewAdapter(
            plants,
            this
        )
        binding.plantListRecyclerview.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(applicationContext, 2)
        }

        setContentView(binding.root)
    }

    override fun onItemClick(position: Int) {
//        Toast.makeText(applicationContext, "Clicked item at position $position", Toast.LENGTH_SHORT).show()
        val spf = applicationContext.getSharedPreferences("PriendPrefs", MODE_PRIVATE)
        spf.edit()
            .putString("plantName",plants.get(position).title)
            .commit()

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(intent)
    }
}