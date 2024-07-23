package com.example.priend.record.view

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.priend.common.db.record.AudioRecordEntity
import com.example.priend.common.db.record.AudioRecordRepository
import com.example.priend.record.domain.audio.AudioManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    @ApplicationContext private val appContext : Context,
    private val audioRecordRepository: AudioRecordRepository
) : ViewModel(){
    private val _currentUri : MutableLiveData<Uri> = MutableLiveData()
    private val currentUri : LiveData<Uri> get() = _currentUri

    private val _isRecording : MutableLiveData<Boolean> = MutableLiveData()
    private val isRecording : LiveData<Boolean> get() = _isRecording


    private val audioManager : AudioManager = AudioManager(appContext, audioRecordRepository)

    init {
        _isRecording.value = false
    }

    fun startRecording(){
        audioManager.startRecord()
        _isRecording.value = true
    }

    fun stopRecording(){
        isRecording.value?.let {
            if (!it){
                return@let
            }
            viewModelScope.launch {
                audioManager.stopRecord()
            }
        }
    }

    fun getCurrentUri() : Uri = currentUri.value!!

    fun isRecording() : Boolean = isRecording.value ?: false

}