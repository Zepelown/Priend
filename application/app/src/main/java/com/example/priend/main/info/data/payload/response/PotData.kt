package com.example.priend.main.info.data.payload.response

import android.util.Log
import java.math.BigDecimal
import java.time.LocalDate

data class PotData(
    val potType: String,
    val plantName: String,
    val plantStartDate: String,
    val plantAge: Short,
    val plantSoilMoisture: BigDecimal,
    val plantTemperature: BigDecimal
){
    fun logDetails(tag: String = "PotData") {
        Log.d(tag, "Pot Type: $potType")
        Log.d(tag, "Plant Name: $plantName")
        Log.d(tag, "Plant Start Date: $plantStartDate")
        Log.d(tag, "Plant Age: $plantAge years")
        Log.d(tag, "Plant Soil Moisture: $plantSoilMoisture")
        Log.d(tag, "Plant Temperature: $plantTemperature°C")
    }
}
