package com.example.priend.main.info.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.priend.R
import com.example.priend.databinding.FragmentInfoDetailBinding
import com.example.priend.main.info.view.list.recyclerview.InfoItem

class InfoDetailFragment : Fragment() {
    private var _binding: FragmentInfoDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoDetailBinding.inflate(layoutInflater)

        val item: InfoItem? = arguments?.getSerializable("item") as? InfoItem

        item?.let {
            Log.d("InfoDetail", item.title)
            binding.infoDetailPlantTitle.text = item.title
            binding.infoDetailPlantName.text = item.title
            binding.infoDetailTemperature.text = item.temperature
            binding.infoDetailStartDate.text = item.startDate
            binding.infoDetailSoilMoisture.text = item.soilMoisture
            binding.infoDetailHumidity.text = item.humidity
            binding.infoDetailIlluminance.text = item.illuminance
        }

        binding.apply {
            infoDetailBackButton.setOnClickListener {
                findNavController().navigate(R.id.action_infoDetailFragment_to_infoFragment)
            }
        }



        return binding.root
    }

}