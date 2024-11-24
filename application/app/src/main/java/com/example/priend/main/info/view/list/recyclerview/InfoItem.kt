package com.example.priend.main.info.view.list.recyclerview

import java.io.Serializable

data class InfoItem(
    val type: InfoItemType,
    val title: String,
    val startDate: String,
    val soilMoisture: String,
    val temperature: String,
) : Serializable