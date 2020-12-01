package za.co.taffy.weatherappassessment.ui.adapters

import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.newFixedThreadPoolContext
import za.co.taffy.weatherappassessment.R
import za.co.taffy.weatherappassessment.data.models.DayForecast
import za.co.taffy.weatherappassessment.data.models.Weather
import za.co.taffy.weatherappassessment.ui.SavedLocationsListActivity
import za.co.taffy.weatherappassessment.ui.SettingsActivity
import za.co.taffy.weatherappassessment.ui.WeatherDetailsActivity
import java.io.Serializable
import java.util.*
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

class WeatherResultsAdapter(private val forecastList: List<DayForecast>) :
    RecyclerView.Adapter<WeatherResultsAdapter.WeatherResultsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherResultsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WeatherResultsViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(viewHolder: WeatherResultsViewHolder, position: Int) {
        val dayForecast: DayForecast? = forecastList[position]
        val weather: Weather = dayForecast!!.weatherList[0]
        val context: Context = viewHolder.itemView.context

        viewHolder.bind(context, weather, dayForecast)

        Glide.with(context)
            .load(("https://openweathermap.org/img/w/" + weather.icon + ".png"))
            .centerCrop()
            .into(viewHolder.weatherIconImageview)

        viewHolder.itemView.setOnClickListener{v : View ->
            val openWeatherDetailsIntent = Intent(context, WeatherDetailsActivity::class.java)
            openWeatherDetailsIntent.putExtra(constantValues.ARG_WEATHER, weather)
            context.startActivity(openWeatherDetailsIntent)
        }

    }

    class WeatherResultsViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
                R.layout.weather_result_recycler_item_layout, parent, false)) {

        var weatherIconImageview: ImageView
        private var dateTextview: TextView? = null
        private var descriptionTextview: TextView? = null
        private var currentTempTextview: TextView? = null
        private var minTempTextview: TextView? = null
        private var maxTempTextview: TextView? = null

        init {
            weatherIconImageview = itemView.findViewById(R.id.weather_icon_imageview)
            dateTextview = itemView.findViewById(R.id.date_textview)
            descriptionTextview = itemView.findViewById(R.id.weather_description_textview)
            currentTempTextview = itemView.findViewById(R.id.current_temprature_textview)
            minTempTextview = itemView.findViewById(R.id.min_temprature_textview)
            maxTempTextview = itemView.findViewById(R.id.max_temprature_textview)
        }

        fun bind(context: Context, weather: Weather, dayForecast: DayForecast) {
            dateTextview!!.text = convertTimeToDate(dayForecast.dateTime)
            descriptionTextview!!.text = weather.description
            currentTempTextview!!.text =
                "${dayForecast.temp.day.roundToInt()}${context.getString(R.string.deg_symbol)}\nnow"
            minTempTextview!!.text =
                "${dayForecast.temp.min.roundToInt()}${context.getString(R.string.deg_symbol)}\nmin"
            maxTempTextview!!.text =
                "${dayForecast.temp.max.roundToInt()}${context.getString(R.string.deg_symbol)}\nmax"

            weatherIconImageview.setOnClickListener { View.OnClickListener{
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
            } }
        }

        private fun convertTimeToDate(currentTimestamp: Long): CharSequence? {
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            calendar.timeInMillis = currentTimestamp * 1000L
            return DateFormat.format("dd MMMM yyyy", calendar).toString()
        }
    }

    object constantValues{
        const val ARG_WEATHER = "weatherObject"
        const val ARG_FORECAST = "forecastObject"
    }


}