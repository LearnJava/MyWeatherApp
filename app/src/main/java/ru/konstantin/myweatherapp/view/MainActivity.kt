package ru.konstantin.myweatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.konstantin.myweatherapp.databinding.ActivityMainBinding
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.service.GeoCityService

lateinit var russianCities: List<GeoCity>
lateinit var capitals: List<GeoCity>

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }

        russianCities = GeoCityService().getAllRussianCities(
            resources.openRawResource(
                resources.getIdentifier(
                    "russiancities",
                    "raw",
                    packageName
                )
            )
        )

        capitals = GeoCityService().getAllRussianCities(
            resources.openRawResource(
                resources.getIdentifier(
                    "capitals",
                    "raw",
                    packageName
                )
            )
        )
    }
}