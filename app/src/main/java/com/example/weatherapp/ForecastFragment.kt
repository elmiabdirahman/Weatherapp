package com.example.weatherapp
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.databinding.FragmentForecastBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    @Inject
    lateinit var viewModel: ForecastViewModel
    private lateinit var binding: FragmentForecastBinding

    private lateinit var theZip : String
    private val args: ForecastFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated( view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastBinding.bind(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        theZip = args.zipCode
    }

    override fun onResume()
    {
        super.onResume()
        viewModel.theForecast.observe(this)
        {
            binding.recyclerView.adapter = MyAdapter(it.list)
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
}