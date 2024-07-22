package com.example.priend.record.audio

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.FileDescriptor

class AudioManager(
    private val appContext : Context
) {
    private val audioRecorder : AudioRecorder = AudioRecorder()
    private val audioConverter: AudioConverter = AudioConverter()
    private val audioFileManager : AudioFileManager = AudioFileManager(appContext)
    private var currentUri : Uri? = null
    fun startRecord(){
        audioFileManager.createFileUri().let {
            currentUri= it
            audioRecorder.record(audioFileManager.getFileDescriptor(it))
            Log.d("record", "녹음 시작")
        }
    }
    fun stopRecord(){
        currentUri?.let {
            val outputFilePath = audioFileManager.getFilePathFromUri(it)
            outputFilePath?.let {
                audioRecorder.stop()
                audioConverter.convertToWav(outputFilePath)
                Log.d("record", "녹음 중지")
            }
        }
    }
}