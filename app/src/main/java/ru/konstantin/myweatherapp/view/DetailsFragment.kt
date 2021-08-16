package ru.konstantin.myweatherapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.konstantin.myweatherapp.R
import ru.konstantin.myweatherapp.databinding.DetailsFragmentBinding
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.model.data.WeatherBigData
import ru.konstantin.myweatherapp.service.*

private const val PROCESS_ERROR = "Обработка ошибки"

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var geoCity: GeoCity

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_RESPONSE_SUCCESS_EXTRA -> {
                    val weather = intent.getStringExtra(DETAILS_WEATHER)
                    weather?.let<String, WeatherBigData> { Json.decodeFromString(it) }
                        ?.let { populateData(it) }
                }
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        geoCity = arguments?.getParcelable<GeoCity>(BUNDLE_EXTRA) ?: GeoCity("", 0.0, 0.0)

        context?.let {
            it.startService(Intent(it, WeatherServiceIntent::class.java).apply {
                putExtra(
                    LATITUDE_EXTRA,
                    geoCity.latitude
                )
                putExtra(
                    LONGITUDE_EXTRA,
                    geoCity.longitude
                )
            })
        }
    }


    private fun populateData(weatherBigData: WeatherBigData) {
        with(binding) {
            cityName.text = geoCity.cityName ?: "---"
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