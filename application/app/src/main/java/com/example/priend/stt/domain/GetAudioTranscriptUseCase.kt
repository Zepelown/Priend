package com.example.priend.stt.domain

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.priend.stt.data.dto.AudioTranscriptDto
import com.example.priend.stt.data.repository.SttRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.IllegalStateException
import javax.inject.Inject

class GetAudioTranscriptUseCase @Inject constructor(
    private val sttRepository: SttRepository,
    @ApplicationContext private val context: Context,
) {
    suspend fun invoke(outputPath: String, fileName: String) : String? {
        val file = File(context.filesDir, outputPath+fileName)  // 실제 파일 경로로 변환 필요

        val requestFile = RequestBody.create("audio/amr".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("audioFile", file.name, requestFile)
        var data : String? = null

        val call  = sttRepository.uploadAudio(body)
            .onSuccess {
                Toast.makeText(context,"통신성공",Toast.LENGTH_SHORT).show()
                return it
            }
            .onFailure {
                Toast.makeText(context,"통신실패",Toast.LENGTH_SHORT).show()
                Log.d("network", it.toString())
                throw NullPointerException("수신 실패1")
            }
//        call.enqueue(object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                Toast.makeText(context,"통신성공",Toast.LENGTH_SHORT).show()
//                data = response.body()
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(context,"통신실패",Toast.LENGTH_SHORT).show()
//                data = null
//            }
//
//        })

        return data
    }

}