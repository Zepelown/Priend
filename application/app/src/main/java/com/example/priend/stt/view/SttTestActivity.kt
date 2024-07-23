package com.example.priend.stt.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.priend.R
import com.example.priend.common.Constants
import com.example.priend.databinding.ActivitySttTestBinding
import com.example.priend.record.view.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class SttTestActivity : AppCompatActivity() {

    private var _binding : ActivitySttTestBinding? = null

    private val binding : ActivitySttTestBinding get() = _binding!!

    private val sttViewModel : SttViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySttTestBinding.inflate(layoutInflater)

        sttViewModel.getAudioTranscript(Constants.OUTPUT_AUDIO_FILE_PATH,Constants.OUTPUT_AUDIO_FILE_PREFIX+ "20240723_075930.amr")

        setContentView(binding.root)
    }
}