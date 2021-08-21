package ru.konstantin.myweatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.konstantin.myweatherapp.R
import ru.konstantin.myweatherapp.databinding.DetailsFragmentBinding
import ru.konstantin.myweatherapp.model.AppState
import ru.konstantin.myweatherapp.model.AppWeatherState
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.model.data.WeatherBigData
import ru.konstantin.myweatherapp.service.WeatherService
import ru.konstantin.myweatherapp.viewmodel.WeatherViewModel

class DetailsFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var geoCity: GeoCity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        geoCity = arguments?.getParcelable<GeoCity>(BUNDLE_EXTRA) ?: GeoCity("", 0.0, 0.0)

        val observer = Observer<AppWeatherState> { appWeatherState ->
            renderData(appWeatherState)
        }
        with(weatherViewModel) {
            getData().observe(viewLifecycleOwner, observer)
            getWeatherFromRemoteSource(geoCity)
        }
    }

    private fun renderData(data: AppWeatherState) {
        when (data) {
            is AppWeatherState.Success -> {
                val weatherData = data.weatheBigDate
                binding.loadingLayout.visibility = View.GONE
                populateData(weatherData)
            }
            is AppWeatherState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppWeatherState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(
                    binding.cityName,
                    resources.getString(R.string.error_text),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(resources.getString(R.string.reload_text)) {
                        weatherViewModel.getWeatherFromRemoteSource(geoCity)
                    }.show()
            }
        }
    }

    private fun populateData(weatherBigData: WeatherBigData) {
        with(binding) {
            cityName.text = geoCity.cityName
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                weatherBigData.location?.lat.toString(),
                weatherBigData.location?.lon.toString()
            )
            temperatureValue.text = weatherBigData.current?.tempC.toString()
            feelsLikeValue.text = weatherBigData.current?.feelslikeC.toString()
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "weatherBigData"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}