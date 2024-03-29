package ru.konstantin.myweatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import ru.konstantin.myweatherapp.R
import ru.konstantin.myweatherapp.databinding.ActivityMainBinding
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.service.GeoCityService

lateinit var russianCities: List<GeoCity>
lateinit var capitals: List<GeoCity>

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCities()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, HistoryFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setCities() {
        russianCities = GeoCityService().getListCitiesFromFile(
            resources.openRawResource(
                resources.getIdentifier(
                    "russiancities",
                    "raw",
                    packageName
                )
            )
        )

        capitals = GeoCityService().getListCitiesFromFile(
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