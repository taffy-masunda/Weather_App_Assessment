package za.co.taffy.weatherappassessment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import za.co.taffy.weatherappassessment.R
import za.co.taffy.weatherappassessment.database.SavedLocation

class SavedLocationsAdapter(private val locationList: List<SavedLocation>) :
    RecyclerView.Adapter<SavedLocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedLocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SavedLocationViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(viewholder: SavedLocationViewHolder, position: Int) {
        val savedLocation: SavedLocation = locationList[position]
        viewholder.bind(savedLocation)
    }

    override fun getItemCount(): Int = locationList.size

}

class SavedLocationViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.saved_location_recycler_item_layout,
            parent,
            false
        )
    ) {
    private var locationName: TextView? = null
    private var locationLatitude: TextView? = null
    private var locationLongitude: TextView? = null


    init {
        locationName = itemView.findViewById(R.id.location_name_textview)
        locationLatitude = itemView.findViewById(R.id.location_latitude_textview)
        locationLongitude = itemView.findViewById(R.id.location_longitude_textview)
    }

    fun bind(location: SavedLocation) {
        locationName?.text =  location.locationName
        locationLatitude?.text = "Latitude \n" + location.locationLatitude.toString()
        locationLongitude?.text = "Longitude \n" + location.locationLongitude.toString()
    }

}