package com.example.priend.common

import android.os.Environment
import com.example.priend.common.db.dummy.Test

object Constants {
    const val OUTPUT_AUDIO_FILE_PREFIX = "[Priend] 녹음 파일_"
    val OUTPUT_AUDIO_FILE_PATH = Environment.DIRECTORY_MUSIC + "/PriendRecord"

    const val BASE_URL = "http://10.0.2.2:8080/"

    fun t(){
        val temp = Test("a")
    }
}