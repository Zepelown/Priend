package com.example.priend.stt.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.priend.common.db.record.AudioRecordRepository
import com.example.priend.stt.domain.GetAudioTranscriptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SttViewModel @Inject internal constructor(
    private val getAudioTranscriptUseCase: GetAudioTranscriptUseCase,
    private val audioRecordRepository: AudioRecordRepository
) : ViewModel() {
    private val _result : MutableLiveData<String> = MutableLiveData()
    val result : LiveData<String> get() = _result

    fun getAudioTranscript(){
        viewModelScope.launch{
            audioRecordRepository.getAudioRecordById(1)?.fileUri?.let {
                getAudioTranscriptUseCase.invoke(it)?.let {
                    _result.value = it
                }
            }
        }
    }

    fun sendTestAudio(){
        viewModelScope.launch {
            _result.value = getAudioTranscriptUseCase.test()
            Log.d("test", result.value.toString())
        }
    }

}