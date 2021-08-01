package ru.konstantin.myweatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.konstantin.myweatherapp.R
import ru.konstantin.myweatherapp.databinding.MainFragmentBinding
import ru.konstantin.myweatherapp.model.AppState
import ru.konstantin.myweatherapp.model.data.WeatherBigData
import ru.konstantin.myweatherapp.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val observer = Observer<AppState> { a ->
            renderData(a)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getWeatherFromRemoteSource()
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                val weatherData = data.weatherData
                binding.loadingLayout.visibility = View.GONE
                populateData(weatherData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.main, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeatherFromRemoteSource() }
                    .show()
            }
        }
    }

    private fun populateData(weatherData: WeatherBigData) {
        with(binding) {
            cityName.text = weatherData.location?.name?:""
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                weatherData.location?.lat?:"",
                weatherData.location?.lon?:""
            )
            temperatureValue.text = weatherData.current?.tempC.toString()?:""
            mainConditionLabel.text = weatherData.current?.condition?.text?:""
        }
    }

}