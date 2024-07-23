package com.example.priend.record.domain.audio

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.priend.common.Constants
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AudioFileManager(
    private val appContext : Context
) {

    fun getFileDescriptor(uri: Uri) = appContext.contentResolver.openFileDescriptor(uri, "w")?.fileDescriptor
        ?: throw IOException("Cannot open file descriptor for URI: $uri")

    fun createFileUri(): Uri {
        val timeStamp = convertDateToFormattedDate(Date())
        val fileName = Constants.OUTPUT_AUDIO_FILE_PREFIX +"$timeStamp"

        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "audio/amr")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Constants.OUTPUT_AUDIO_FILE_PATH)
        }

        return appContext.contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
            ?: throw IOException("파일 경로 생성 오류 발생")
    }

    fun getFilePathFromUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        var filePath: String? = null
        appContext.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                filePath = cursor.getString(columnIndex)
            }
        }
        return filePath
    }

    fun convertDateToFormattedDate(date: Date) = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(date)

}