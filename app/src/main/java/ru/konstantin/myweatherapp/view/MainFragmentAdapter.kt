package ru.konstantin.myweatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.konstantin.myweatherapp.databinding.MainRecyclerItemBinding
import ru.konstantin.myweatherapp.model.data.GeoCity

class MainFragmentAdapter :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>(), Filterable {

    private var geoCities = mutableListOf<GeoCity>()
    private var geoCitiesAll: List<GeoCity> = listOf()

    private var onItemViewClickListener: MainFragment.OnItemViewClickListener? = null

    fun setOnItemViewClickListener(onItemViewClickListener: MainFragment.OnItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener
    }

    fun removeOnItemViewClickListener() {
        onItemViewClickListener = null
    }

    fun setWeather(data: List<GeoCity>) {
        geoCities = data.toMutableList()
        geoCitiesAll = data
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

    inner class MainViewHolder(val binding: MainRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(geoCity: GeoCity) {
            binding.mainFragmentRecyclerItemTextView.text = geoCity.cityName
            binding.root.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(geoCity)
                Toast.makeText(
                    itemView.context,
                    geoCity.cityName,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun getFilter(): Filter? {
        return myFilter
    }

    var myFilter: Filter = object : Filter() {
        //Automatic on background thread
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filteredList: MutableList<GeoCity> = ArrayList()
            if (charSequence.isEmpty()) {
                filteredList.addAll(geoCitiesAll)
            } else {
                for (geoCity in geoCitiesAll) {
                    if (geoCity.cityName.toLowerCase()
                            .contains(charSequence.toString().toLowerCase())
                    ) {
                        filteredList.add(geoCity)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        //Automatic on UI thread
        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            geoCities.clear()
            geoCities.addAll(filterResults.values as Collection<GeoCity>)
            notifyDataSetChanged()
        }
    }
}