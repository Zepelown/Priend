package com.example.priend.stt.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.priend.stt.domain.GetAudioTranscriptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SttViewModel @Inject internal constructor(
    private val getAudioTranscriptUseCase: GetAudioTranscriptUseCase
) : ViewModel() {
    private val _result : MutableLiveData<String> = MutableLiveData()
    private val result : LiveData<String> get() = _result

    fun getAudioTranscript(outputPath: String, fileName: String){
        viewModelScope.launch{
            getAudioTranscriptUseCase.invoke(outputPath,fileName)?.let {
                _result.value = it
            }

        }
    }

}