package com.example.priend.main.info.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.priend.R
import com.example.priend.databinding.FragmentInfoDetailBinding

class InfoDetailFragment : Fragment() {
    private var _binding: FragmentInfoDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoDetailBinding.inflate(layoutInflater)



        return binding.root
    }

}