package com.example.priend.record.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.priend.databinding.ActivityRecordBinding
import com.example.priend.stt.view.SttTestActivity
import com.example.priend.tts.view.TtsTestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordActivity : AppCompatActivity() {

    private var _binding : ActivityRecordBinding? = null

    private val binding get() = _binding!!

    private val recordViewModel : RecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRecordBinding.inflate(layoutInflater)

        requestPermissions()

        binding.apply {
            startRecordButton.setOnClickListener {
                recordViewModel.startRecording()
            }

            endRecordButton.setOnClickListener {
                recordViewModel.stopRecording()
            }

            moveSttButton.setOnClickListener {
                val intent = Intent(applicationContext, SttTestActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                applicationContext.startActivity(intent)
            }

            moveTtsButton.setOnClickListener {
                val intent = Intent(applicationContext, TtsTestActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                applicationContext.startActivity(intent)
            }

        }

        setContentView(binding.root)
    }


    private fun requestPermissions() {
        if (permissions.all {
                ContextCompat.checkSelfPermission(applicationContext, it) == PackageManager.PERMISSION_GRANTED
            }) {
        } else {
            ActivityCompat.requestPermissions(this, permissions, 100)
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