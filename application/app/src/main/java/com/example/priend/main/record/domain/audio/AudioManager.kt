package com.example.priend.main.record.domain.audio

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.priend.common.db.record.AudioRecordEntity
import com.example.priend.common.db.record.AudioRecordRepository
import java.util.Date

class AudioManager(
    private val appContext : Context,
    private val audioRecordRepository: AudioRecordRepository
) {
    private val audioRecorder : AudioRecorder = AudioRecorder()
    private val audioConverter: AudioConverter = AudioConverter()
    private val audioFileManager : AudioFileManager = AudioFileManager(appContext)
    private var currentUri : Uri? = null
    private var startDate : Date? = null
    fun startRecord(){
        startDate = Date()
        audioFileManager.createFileUri(startDate!!).let {
            currentUri= it
            audioRecorder.record(audioFileManager.getFileDescriptor(it))
            Log.d("record", "녹음 시작")
        }
    }
    suspend fun stopRecord(){
        currentUri?.let {
            val outputFilePath = audioFileManager.getFilePathFromUri(it)
            outputFilePath?.let {
                audioRecorder.stop()
                audioRecordRepository.insertAudioRecord(
                    AudioRecordEntity(
                        title = audioFileManager.convertDateToFormattedDate(startDate!!),
                        fileUriString = currentUri.toString()
                    )
                )
              audioConverter.convertToWav(outputFilePath)
                Log.d("record", "녹음 중지")
            }
        }
    }
}