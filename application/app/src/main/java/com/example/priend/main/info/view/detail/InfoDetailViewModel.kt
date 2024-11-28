package com.example.priend.main.info.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.priend.main.info.domain.GetPotDataUseCase
import com.example.priend.main.info.view.list.recyclerview.InfoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoDetailViewModel @Inject constructor (
    private val getPotDataUseCase: GetPotDataUseCase
) : ViewModel(){
    private val _result: MutableLiveData<InfoItem> = MutableLiveData()
    val result: LiveData<InfoItem> get() = _result

    fun startPolling(potId: Double, intervalMillis: Long) {
        viewModelScope.launch {
            while (true) {
                getPotData(potId)
                delay(intervalMillis)
            }
        }
    }

    private fun getPotData(potId: Double) {
        viewModelScope.launch {
            getPotDataUseCase.invoke(potId).let {
                _result.value = it
            }
        }
    }
}