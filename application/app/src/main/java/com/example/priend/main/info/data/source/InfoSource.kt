package com.example.priend.main.info.data.source

import com.example.priend.common.payload.CommonResponse
import com.example.priend.main.info.data.payload.reqeust.PotCensorRequest
import com.example.priend.main.info.data.payload.response.PotData
import com.example.priend.main.info.data.payload.response.PotDataContainer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InfoSource {
    @POST("plant/censor/data")
    suspend fun getPotCensorData(@Body potCensorRequest: PotCensorRequest) : Response<CommonResponse<PotDataContainer>>
}