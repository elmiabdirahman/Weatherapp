package com.example.weatherapp
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.FragmentCurrentConditionsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import retrofit2.HttpException


@AndroidEntryPoint
class CurrentConditionsFragment : Fragment() {
    @Inject
    lateinit var viewModel: CurrentConditionsViewModel
    private lateinit var binding: FragmentCurrentConditionsBinding
    private lateinit var theZip: String
    private val args: CurrentConditionsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_conditions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCurrentConditionsBinding.bind(view)

        theZip = args.zipCode

        binding.button.setOnClickListener {navigationToForecast()
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.currentConditions.observe(this){
                currentConditions -> bindData(currentConditions)
        }

        try
        {
            viewModel.loadData(theZip)
        }
        catch (e: HttpException)
        {

            ErrorDialogFragment().show(childFragmentManager, "")

        }
    }
    @SuppressLint("SetTextI18n")
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


    private fun navigationToForecast()
    {
        val action = CurrentConditionsFragmentDirections.actionCurrentConditionsFragmentToForecastFragment(theZip)
        findNavController().navigate(action)
    }
}