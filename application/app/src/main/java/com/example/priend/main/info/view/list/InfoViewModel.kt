package com.example.priend.main.info.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.priend.main.info.domain.GetPotDataUseCase
import com.example.priend.main.info.view.list.recyclerview.InfoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val getPotDataUseCase: GetPotDataUseCase
) : ViewModel() {
    private val _result: MutableLiveData<List<InfoItem>> = MutableLiveData()
    val result: LiveData<List<InfoItem>> get() = _result

    fun getPotData(potId: Double) {
        viewModelScope.launch {
            getPotDataUseCase.invoke(potId).let {
                _result.value = listOf(it)
            }
        }
    }
}