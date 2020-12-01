package za.co.taffy.weatherappassessment.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import za.co.taffy.weatherappassessment.R
import za.co.taffy.weatherappassessment.data.RetrofitBuilder
import za.co.taffy.weatherappassessment.ui.adapters.WeatherResultsAdapter


class WeatherResultsListActivity : AppCompatActivity() {

    private lateinit var weatherResultsRecyclerView: RecyclerView
    lateinit var locationNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_results_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        val locationLatitude = intent.getDoubleExtra("locationLatitude", 0.0)
        val locationLongitude = intent.getDoubleExtra("locationLongitude", 0.0)
        val locationName = intent.getStringExtra("locationName")

        setupViews()
        locationNameTextView.text = locationName
        setupRecyclerView(locationLatitude.toString(), locationLongitude.toString())
    }

    private fun setupViews() {
        locationNameTextView = findViewById(R.id.location_name_textview)
        weatherResultsRecyclerView = findViewById(R.id.weather_results_recycler_view)
    }

    private suspend fun getWeatherInformation(latitude: String, longitude: String) {
        val apiService = RetrofitBuilder.apiService
        val response = apiService.getFiveDaysForecast(latitude, longitude)
        weatherResultsRecyclerView.adapter = WeatherResultsAdapter(response.list)
    }

    private fun setupRecyclerView(latitude: String, longitude: String) {

        CoroutineScope(Dispatchers.Main).launch {
            getWeatherInformation(latitude, longitude)
        }

        weatherResultsRecyclerView.apply {
            recycledViewPool.apply {
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}