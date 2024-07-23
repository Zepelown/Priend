package com.example.priend.stt.domain

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.example.priend.R
import com.example.priend.stt.data.repository.SttRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class GetAudioTranscriptUseCase @Inject constructor(
    private val sttRepository: SttRepository,
    @ApplicationContext private val context: Context,
) {
    suspend fun invoke(uri: Uri): String? {
//        val file = File(context.filesDir, uri)  // 실제 파일 경로로 변환 필요
//
//        val requestFile = RequestBody.create("audio/amr".toMediaTypeOrNull(), file)
//        val body = MultipartBody.Part.createFormData("audioFile", file.name, requestFile)

        val file = getFileFromUri(context, uri) ?: return null

        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("audioFile", file.name, requestFile)

        val transcript = sttRepository.uploadAudio(body)

        return transcript.toString()
    }

    suspend fun test(): String? {
        val resId = R.raw.test_audio // `example.amr` 파일의 리소스 ID
        val file = getFileFromRawResource(context, resId, "example.amr") ?: return null

        val requestFile = RequestBody.create("audio/amr".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("audioFile", file.name, requestFile)

        return try {
            val result = sttRepository.uploadAudio(body)
            result.getOrElse {
                Log.d("stt-api", "Error occurred: ${it.message}")
                null
            }
        } catch (e: Exception) {
            Log.d("stt-api", "Exception occurred: ${e.message}")
            null
        }
    }

    fun getFileFromUri(context: Context, uri: Uri): File? {
        val fileName = getFileName(context, uri)
        val file = File(context.cacheDir, fileName)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var len: Int
            while (inputStream!!.read(buffer).also { len = it } != -1) {
                outputStream.write(buffer, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return file
    }

    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (it != null && it.moveToFirst()) {
                    result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result!!
    }

    private fun getFileFromRawResource(context: Context, resId: Int, fileName: String): File? {
        val inputStream: InputStream = context.resources.openRawResource(resId)
        val file = File(context.cacheDir, fileName)

        try {
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length: Int

            while (inputStream.read(buffer).also { length = it } != -1) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return file
    }

}