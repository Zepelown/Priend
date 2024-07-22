package com.example.priend.stt.data.source

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SttService {
    @Multipart
    @POST("stt/audio")
    suspend fun postSpeechRecognitionResult(@Part audioFile : MultipartBody.Part ) : Response<String>
}