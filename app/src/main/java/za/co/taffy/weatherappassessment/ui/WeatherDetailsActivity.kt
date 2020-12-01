package za.co.taffy.weatherappassessment.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import za.co.taffy.weatherappassessment.R
import za.co.taffy.weatherappassessment.data.models.DayForecast
import za.co.taffy.weatherappassessment.data.models.ForecastResponse
import za.co.taffy.weatherappassessment.data.models.Weather
import za.co.taffy.weatherappassessment.ui.adapters.WeatherResultsAdapter

class WeatherDetailsActivity : AppCompatActivity() {


    lateinit var weatherIconImageView : ImageView
    lateinit var headingTextview : TextView
    lateinit var descriptionTextview : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
        setSupportActionBar(findViewById(R.id.toolbar))

        val weatherObject: Weather = intent.getSerializableExtra(WeatherResultsAdapter.constantValues.ARG_WEATHER) as Weather

        setupViews(weatherObject)
    }

    private fun setupViews(weather: Weather) {
        weatherIconImageView = findViewById(R.id.icon_imageview)
        headingTextview = findViewById(R.id.heading_textview)
        descriptionTextview = findViewById(R.id.description_textview)

        Glide.with(this)
            .load(("https://openweathermap.org/img/w/" + weather.icon + ".png"))
            .centerCrop()
            .into(weatherIconImageView)

        headingTextview.text = weather.main
        descriptionTextview.text = weather.description
    }
}