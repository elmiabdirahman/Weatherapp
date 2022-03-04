package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val service: Api): ViewModel() {
    private val outForecast = MutableLiveData<Forecast>()
    val forecast: LiveData<Forecast>
        get() = outForecast

    fun loadData() = runBlocking {
        launch {
            outForecast.value = service.getForecast()
        }
    }
}