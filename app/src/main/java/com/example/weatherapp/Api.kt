package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
   suspend fun getCurrentConditions(
        @Query("zip") zip: String = "55077",
        @Query("units") units: String = "imperial",
        @Query("appId") appId: String = "624f025e07a9048b7dc5fc9fd6717018",
    ): CurrentConditions
    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("zip") zip: String = "55077",
        @Query("units") units: String = "imperial",
        @Query("appId") appId: String = "624f025e07a9048b7dc5fc9fd6717018",
        @Query("cnt") cnt: Int = 16
    ): Forecast
}