package ru.konstantin.myweatherapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.konstantin.myweatherapp.R
import ru.konstantin.myweatherapp.databinding.MainFragmentBinding
import ru.konstantin.myweatherapp.model.AppStateCity
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.service.EMPTY_SIGN
import ru.konstantin.myweatherapp.viewmodel.ViewModelCity

private const val IS_RUSSIAN_KEY = "LIST_OF_RUSSIAN_KEY"

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModelCity: ViewModelCity
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
        viewModelCity = ViewModelProvider(this).get(ViewModelCity::class.java)
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
                saveListOfTowns()
            }

            contactListButton.setOnClickListener() {
                val manager = activity?.supportFragmentManager

                manager?.let {
                    manager.beginTransaction()
                        .replace(R.id.container, ContactsFragment.newInstance())
                        .addToBackStack(EMPTY_SIGN)
                        .commitAllowingStateLoss()
                }
            }
        }
        val observer = Observer<AppStateCity> {
            renderData(it)
        }

        with(viewModelCity) {
            getData().observe(viewLifecycleOwner, observer)
            loadListOfTowns()
            GlobalScope.launch {
                getCityList(isDataSetRus)
            }
        }
    }

    private fun loadListOfTowns() {
        requireActivity().apply {
            isDataSetRus = getPreferences(Context.MODE_PRIVATE).getBoolean(IS_RUSSIAN_KEY, true)
        }
    }

    private fun saveListOfTowns() {
        requireActivity().apply {
            getPreferences(Context.MODE_PRIVATE).edit {
                putBoolean(IS_RUSSIAN_KEY, isDataSetRus)
                apply()
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
        viewModelCity.getCityList(isDataSetRus)
        binding.mainFragmentFAB.setImageResource(icon)
    }

    @DelicateCoroutinesApi
    private fun renderData(data: AppStateCity) {
        when (data) {
            is AppStateCity.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapter.setWeather(data.geocityList)
            }
            is AppStateCity.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is AppStateCity.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(
                    binding.mainFragmentFAB,
                    resources.getString(R.string.error_text),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(resources.getString(R.string.reload_text)) {
                        if (isDataSetRus) {
                            viewModelCity.getCityList(isDataSetRus)
                        } else {
                            viewModelCity.getCityList(isDataSetRus)
                        }
                    }.show()
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(geoCity: GeoCity)
    }
}