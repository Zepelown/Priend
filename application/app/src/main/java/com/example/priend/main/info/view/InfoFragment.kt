package com.example.priend.main.info.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.priend.common.view.RecyclerViewItemClickListener
import com.example.priend.databinding.FragmentInfoBinding
import com.example.priend.main.info.view.recyclerview.InfoItem
import com.example.priend.main.info.view.recyclerview.InfoItemType
import com.example.priend.main.info.view.recyclerview.InfoViewAdapter
import com.example.priend.stt.view.SttViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InfoFragment : Fragment(), RecyclerViewItemClickListener {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val infoViewModel : InfoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(layoutInflater)

        val adapter = InfoViewAdapter(
            plantsInfo,
            this
        )

        binding.plantInfoList.apply {
            this.adapter = adapter
        }

        infoViewModel.getPotData(1.0)

        return binding.root
    }

    companion object {
        private val plantsInfo = listOf<InfoItem>(
            InfoItem(InfoItemType.PLANT, "test", "2024.01.01", "10", "10c"),
            InfoItem(InfoItemType.ADD, "test", "2024.01.01", "10", "10c"),
        )
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}