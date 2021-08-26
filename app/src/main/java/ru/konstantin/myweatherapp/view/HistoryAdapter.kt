package ru.konstantin.myweatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.konstantin.myweatherapp.databinding.HistoryRecyclerItemBinding
import ru.konstantin.myweatherapp.model.data.WeatherBigData
import ru.konstantin.myweatherapp.model.data.WeatherHistory

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var data: List<WeatherHistory> = arrayListOf()

    fun setData(data: List<WeatherHistory>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val binding = HistoryRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class HistoryViewHolder(private val binding: HistoryRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data:WeatherHistory){
            binding.apply {
                cityName.text = data.city
                weatherCondition.text = data.condition
                weatherTemperature.text = data.temperature.toString()
            }
        }
    }
}