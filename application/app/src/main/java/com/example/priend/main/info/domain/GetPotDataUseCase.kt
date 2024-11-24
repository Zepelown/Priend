package com.example.priend.main.info.domain

import android.util.Log
import com.example.priend.main.info.data.payload.reqeust.PotCensorRequest
import com.example.priend.main.info.data.repository.InfoRepository
import com.example.priend.main.info.view.list.recyclerview.InfoItem
import com.example.priend.main.info.view.list.recyclerview.InfoItemType
import javax.inject.Inject

class GetPotDataUseCase @Inject constructor(
    private val infoRepository: InfoRepository
) {
    suspend fun invoke(potId: Double) : InfoItem {
        val response = infoRepository.getPotData(PotCensorRequest(potId))

        return response.getOrElse {
            Log.d("censor", "수신 실패: ${it.message}")
            throw it
        }.let {
            it.potData.logDetails()
            InfoItem(
                type = InfoItemType.PLANT,
                title = it.potData.plantName,
                startDate = it.potData.plantStartDate.toString(),
                soilMoisture = it.potData.plantSoilMoisture.toString(),
                temperature = it.potData.plantTemperature.toString(),
                humidity = it.potData.plantHumidity.toString(),
                illuminance = it.potData.plantIlluminance.toString()
            )
        }
    }
}