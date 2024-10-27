package com.example.priend.main.record.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.priend.databinding.FragmentRecordBinding

class RecordFragment : Fragment() {

    private var _binding : FragmentRecordBinding? = null

    private val binding get() = _binding!!

    private val recordViewModel : RecordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBinding.inflate(layoutInflater)

        requestPermissions()

        binding.apply {
            startRecordButton.setOnClickListener {
                recordViewModel.startRecording()
            }

            endRecordButton.setOnClickListener {
                recordViewModel.stopRecording()
            }

        }

        return binding.root
    }


    private fun requestPermissions() {
        if (permissions.all {
                ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
            }) {
        } else {
            ActivityCompat.requestPermissions(requireActivity(), permissions, 100)
        }
    }

    companion object{
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }


}