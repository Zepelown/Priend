package com.example.priend.tts.view

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.priend.databinding.ActivityTtsTestBinding
import java.util.Locale


class TtsTestActivity : AppCompatActivity()  {

    private var _binding : ActivityTtsTestBinding? = null

    private val binding get() = _binding!!

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTtsTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(applicationContext, null)
        binding.apply {
            ttsInputSubmitButton.setOnClickListener {
                speakOut()
            }
        }
    }

    private fun speakOut() {
        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            when (status) {
                TextToSpeech.SUCCESS -> {
                    val result = tts?.setLanguage(Locale.KOREA)
                    when (result) {
                        TextToSpeech.LANG_MISSING_DATA -> {
                            Log.e("TTS", "Language data is missing")
                            // Prompt user to install missing data
                        }
                        TextToSpeech.LANG_NOT_SUPPORTED -> {
                            Log.e("TTS", "Language is not supported")
                            // Handle unsupported language
                        }
                        else -> {
                            // Initialization successful
                            binding.ttsInputSubmitButton.isEnabled = true
                        }
                    }
                }
                TextToSpeech.ERROR -> {
                    Log.e("TTS", "TextToSpeech initialization error")
                    // Handle initialization error
                    binding.ttsInputSubmitButton.isEnabled = false
                }
                else -> {
                    Log.e("TTS", "Unknown initialization status: $status")
                    binding.ttsInputSubmitButton.isEnabled = false
                }
            }

            val text = binding.ttsInputEdittext.text.toString()
            if (text.isNotEmpty()) {
                tts!!.apply {
                    setSpeechRate(0.1f) // 음성 속도 지정
                    speak(text, TextToSpeech.QUEUE_FLUSH, null, "id1")
                }
            }
        })
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        _binding = null
        super.onDestroy()
    }
}