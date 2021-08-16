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
import kotlinx.coroutines.launch
import ru.konstantin.myweatherapp.R
import ru.konstantin.myweatherapp.databinding.MainFragmentBinding
import ru.konstantin.myweatherapp.model.AppState
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.service.EMPTY_SIGN
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

    private lateinit var cityList: List<GeoCity>

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
        adapter.removeOnItemViewClickListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        cityList = russianCities
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(geoCity: GeoCity) {
                val manager = activity?.supportFragmentManager

                manager?.let {
                    val bundle = Bundle()
                    bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, geoCity)
                    manager.beginTransaction()
                        .replace(R.id.container, DetailsFragment.newInstance(bundle))
                        .addToBackStack(EMPTY_SIGN)
                        .commitAllowingStateLoss()
                }
            }
        })

        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener {
                changeWeatherDataSet()
            }
        }
        val observer = Observer<AppState> {
            renderData(it)
        }

        with(viewModel) {
            getData().observe(viewLifecycleOwner, observer)
            GlobalScope.launch {
                getWeatherFromRemoteSource(isDataSetRus)
            }
        }
    }

    private fun changeWeatherDataSet() {
        isDataSetRus = !isDataSetRus

        when (isDataSetRus) {
            true -> getData(russianCities, R.drawable.ic_russia)
            else -> getData(capitals, R.drawable.ic_earth)
        }
    }

    private fun getData(cities: List<GeoCity>, icon: Int) {
        cityList = cities
        viewModel.getWeatherFromRemoteSource(isDataSetRus)
        binding.mainFragmentFAB.setImageResource(icon)
    }

    @DelicateCoroutinesApi
    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapter.setWeather(data.geocityList)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(
                    binding.mainFragmentFAB,
                    resources.getString(R.string.error_text),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(resources.getString(R.string.reload_text)) {
                        if (isDataSetRus) {
                            viewModel.getWeatherFromRemoteSource(isDataSetRus)
                        } else {
                            viewModel.getWeatherFromRemoteSource(isDataSetRus)
                        }
                    }.show()
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(geoCity: GeoCity)
    }
}