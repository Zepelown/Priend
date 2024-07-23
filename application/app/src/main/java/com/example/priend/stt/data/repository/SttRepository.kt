package com.example.priend.stt.data.repository

import com.example.priend.stt.data.source.SttService
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import javax.inject.Inject

class SttRepository @Inject constructor(
    private val sttService: SttService
) {
    suspend fun uploadAudio(audio: MultipartBody.Part) = kotlin.runCatching {
        val response = sttService.postSpeechRecognitionResult(audio)
        if (response.isSuccessful) {
            response.body() ?: throw RuntimeException("바디 없음")
        } else throw RuntimeException("통신 실패2")
    }
}