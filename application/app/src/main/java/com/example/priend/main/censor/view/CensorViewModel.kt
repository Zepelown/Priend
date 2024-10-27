package com.example.priend.main.censor.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.priend.R
import com.example.priend.common.TextWithColor

class CensorViewModel : ViewModel() {
    private val _soilMoistureCondition = MutableLiveData<Int>()

    val soilMoistureCondition: LiveData<Int> get() = _soilMoistureCondition


    private val _airMoistureCondition = MutableLiveData<Int>()

    val airMoistureCondition: LiveData<Int> get() = _airMoistureCondition


    private val _airTemperatureCondition = MutableLiveData<Int>()

    val airTemperatureCondition: LiveData<Int> get() = _airTemperatureCondition

    private var soilMoistureStandard : Int = 600
    private var airMoistureStandard : Int = 40 //low 기준


    init {
        _soilMoistureCondition.value = 0
        _airMoistureCondition.value = 0
        _airTemperatureCondition.value = 100
    }

    fun setSoilMoistureCondition(value: Int) {
        _soilMoistureCondition.value = value
    }

    fun setAirMoistureCondition(value: Int){
        _airMoistureCondition.value = value
    }
    fun setAirTemperatureCondition(value: Int){
        _airTemperatureCondition.value = value
        Log.d("MainViewModel", "Soil moisture condition updated: $value")
    }

//    fun getSoilMoistureColor() : Int = if (soilMoistureCondition.value!! < 900) ContextCompat.getColor(R.color.skyblue) else ContextCompat.getColor(R.color.skyblue)

    fun getSoilMoistureStatus(): TextWithColor {
        var text : String
        var color : Int
        if (soilMoistureCondition.value!! > soilMoistureStandard){
            text = "건조"
            color = R.color.red
        } else {
            text = "습함"
            color = R.color.skyblue
        }
        return TextWithColor(text, color)
    }

    fun getAirMoistureStatus() : TextWithColor =
        when(airMoistureCondition.value) {
            in 0 .. airMoistureStandard -> TextWithColor("건조", R.color.red)
            in airMoistureStandard+1 .. airMoistureStandard+20 -> TextWithColor("적당", R.color.green)
            in airMoistureStandard+21 .. 100 -> TextWithColor("습함", R.color.skyblue)
            else -> TextWithColor("오류", R.color.purple_200)
        }

    fun getAirTemperature() : String = airTemperatureCondition.value.toString()

    fun setSoilMoistureStandard(int : Int) {soilMoistureStandard = int}
    fun setAirMoistureStandard(int: Int) {airMoistureStandard = int}

}

