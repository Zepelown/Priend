package com.example.priend.main.info.view.list.recyclerview

import java.io.Serializable
import java.math.BigDecimal

data class InfoItem(
    val type: InfoItemType,
    val title: String,
    val startDate: String,
    val soilMoisture: String,
    val temperature: String,
    val humidity: String,
    val illuminance: String
) : Serializable