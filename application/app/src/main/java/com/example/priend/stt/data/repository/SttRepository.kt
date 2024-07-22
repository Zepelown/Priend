package com.example.priend.stt.data.repository

import com.example.priend.stt.data.source.SttService
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import javax.inject.Inject

class SttRepository @Inject constructor(
    private val sttService: SttService
) {
    suspend fun uploadAudio(audio: MultipartBody.Part) = sttService.postSpeechRecognitionResult(audio)
}