package za.co.taffy.weatherappassessment.ui.adapters

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import za.co.taffy.weatherappassessment.R
import za.co.taffy.weatherappassessment.data.models.DayForecast
import za.co.taffy.weatherappassessment.data.models.Weather
import java.util.*
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

class WeatherResultsAdapter(private val forecastList: List<DayForecast>) :
    RecyclerView.Adapter<WeatherResultsAdapter.WeatherResultsViewHolder>() {

    //private var forcastList: List<DayForecast> = listOf()

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

        viewHolder.bind(context, weather,dayForecast)

//        Glide.with(context)
//            .load(createIconUrl(weather.icon))
//            .centerCrop()
//            .into(viewHolder.weatherIconImageview)
    }

    class WeatherResultsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(
            inflater.inflate(
                R.layout.weather_result_recycler_item_layout,
                parent,
                false
            )
        ) {
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
            currentTempTextview!!.text = dayForecast.temp.day.roundToInt().toString()
            minTempTextview!!.text = dayForecast.temp.min.roundToInt().toString()
            maxTempTextview!!.text = dayForecast.temp.max.roundToInt().toString()

            Glide.with(context)
                .load((createIconUrl(weather.icon)))
                .centerCrop()
                .into(weatherIconImageview)
        }

        private fun convertTimeToDate(currentTimestamp: Long): CharSequence? {
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            calendar.timeInMillis = currentTimestamp * 1000L
            return DateFormat.format("dd MMMM yyyy", calendar).toString()
        }

        private fun createIconUrl(icon: String): String? {
            return "http://openweathermap.org/img/w/{$icon}.png"
        }
    }

}