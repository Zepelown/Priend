package com.example.priend.main.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.priend.common.view.RecyclerViewItemClickListener
import com.example.priend.databinding.FragmentInfoBinding
import com.example.priend.main.info.recyclerview.InfoItem
import com.example.priend.main.info.recyclerview.InfoViewAdapter
import com.example.priend.main.info.recyclerview.InfoViewHolder


class InfoFragment : Fragment(), RecyclerViewItemClickListener {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
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


        return binding.root
    }

    companion object{
        private val plantsInfo = listOf<InfoItem>(
            InfoItem("test","2024.01.01","10","10c")
        )
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}