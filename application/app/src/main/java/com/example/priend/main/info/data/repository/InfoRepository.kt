package com.example.priend.main.info.data.repository

import android.util.Log
import com.example.priend.main.info.data.payload.reqeust.PotCensorRequest
import com.example.priend.main.info.data.payload.response.PotData
import com.example.priend.main.info.data.payload.response.PotDataContainer
import com.example.priend.main.info.data.source.InfoSource
import javax.inject.Inject

class InfoRepository @Inject constructor(
    private val infoSource: InfoSource
) {
    suspend fun getPotData(potCensorRequest: PotCensorRequest): Result<PotDataContainer> {
        return kotlin.runCatching {
            val response = infoSource.getPotCensorData(potCensorRequest)
            Log.d("InfoRepository", "Response: ${response.body()}")
            if (response.isSuccessful) {
                val data = response.body()?.data
                Log.d("InfoRepository","${data}")
                if (data != null) {
                    return@runCatching data
                } else {
                    throw RuntimeException("바디 없음: ${response.body()?.message}")
                }
            } else {
                throw RuntimeException("통신 실패: ${response.errorBody()?.string()}")
            }
        }
    }
}