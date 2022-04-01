package com.example.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val service: Api): ViewModel() {
    val theForecast : MutableLiveData<Forecast> = MutableLiveData()


    fun loadData(upZip: String) = runBlocking {
        launch {
            theForecast.value = service.getForecast(upZip)
        }
    }
}