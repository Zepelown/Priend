package com.example.priend.main.info.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.priend.R
import com.example.priend.databinding.FragmentInfoDetailBinding
import com.example.priend.main.info.view.list.InfoViewModel
import com.example.priend.main.info.view.list.recyclerview.InfoItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoDetailFragment : Fragment() {
    private var _binding: FragmentInfoDetailBinding? = null
    private val binding get() = _binding!!

    private val infoViewModel: InfoDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoDetailBinding.inflate(layoutInflater)

        infoViewModel.startPolling(potId = 1.0, intervalMillis = 5000L) // 5초마다 실행

        infoViewModel.result.observe(viewLifecycleOwner) { item ->
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