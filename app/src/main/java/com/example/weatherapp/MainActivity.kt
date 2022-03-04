package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityMainBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            startActivity(Intent(this, ForecastActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.currentConditions.observe(this){
                currentConditions -> bindData(currentConditions)
        }
        viewModel.loadData()
    }


    private fun bindData(currentConditions: CurrentConditions) {
        binding.cityName.text = currentConditions.name
        binding.temperature.text = getString(R.string.temperature, currentConditions.main.temp.toInt())
        binding.feelLike.text = getString(R.string.feels_like, currentConditions.main.feelsLike.toInt())
        binding.lowTem.text = getString(R.string.low_tem, currentConditions.main.tempMin.toInt())
        binding.HighTem.text = getString(R.string.high_tem, currentConditions.main.tempMax.toInt())
        binding.HumidityTem.text = getString(R.string.humidity_tem, currentConditions.main.humidity.toInt())
        binding.pressure.text = getString(R.string.pressure, currentConditions.main.pressure.toInt())

        val weather = currentConditions.weather.firstOrNull()
        weather?.let {
            Glide.with(this)
                .load("https://openweathermap.org/img/wn/${it.icon}@2x.png")
                .into(binding.conditionIcon)
        }
    }


}