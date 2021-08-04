package ru.konstantin.myweatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.konstantin.myweatherapp.databinding.MainRecyclerItemBinding
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.model.data.WeatherBigData

class MainFragmentAdapter :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var geoCities: List<GeoCity> = listOf()

    fun setWeather(data: List<GeoCity>) {
        geoCities = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = MainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(geoCities[position])
    }

    override fun getItemCount(): Int = geoCities.count()

    class MainViewHolder(val binding: MainRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(geoCity: GeoCity) {
            binding.mainFragmentRecyclerItemTextView.text = geoCity.cityName
            binding.root.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    geoCity.cityName,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}