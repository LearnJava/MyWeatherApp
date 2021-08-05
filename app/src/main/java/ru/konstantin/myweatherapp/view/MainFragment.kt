package ru.konstantin.myweatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
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

    private val adapter = MainFragmentAdapter()
    private var isDataSetRus: Boolean = true

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

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
            changeWeatherDataSet()
        }
        val observer = Observer<AppState> { a ->
            renderData(a)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        GlobalScope.launch {
            viewModel.getWeatherFromRemoteSource(isDataSetRus)
        }
    }

    private fun changeWeatherDataSet() {
        isDataSetRus = !isDataSetRus

        if (isDataSetRus) {
            viewModel.getWeatherFromRemoteSource()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            cityList = capitals
            viewModel.getWeatherFromRemoteSource(isDataSetRus)
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        }
    }

    @DelicateCoroutinesApi
    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                val weatherData = data.weatherData
                binding.loadingLayout.visibility = View.GONE
                adapter.setWeather(weatherData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainFragmentFAB, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") {
                        if (isDataSetRus) viewModel.getWeatherFromRemoteSource(isDataSetRus)
                        else viewModel.getWeatherFromRemoteSource(isDataSetRus)
                    }
                    .show()
            }
        }
    }
}