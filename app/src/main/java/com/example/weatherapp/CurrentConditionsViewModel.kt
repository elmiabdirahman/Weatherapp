package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CurrentConditionsViewModel @Inject constructor(private val service: Api): ViewModel()
{

    val currentConditions : MutableLiveData<CurrentConditions> = MutableLiveData()

    fun loadData(upZip : String) = runBlocking {
        launch{
            currentConditions.value = service.getCurrentConditions(upZip)
        }
    }


}