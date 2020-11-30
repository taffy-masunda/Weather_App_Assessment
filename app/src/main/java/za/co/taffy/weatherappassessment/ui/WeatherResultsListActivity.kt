package za.co.taffy.weatherappassessment.ui

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import za.co.taffy.weatherappassessment.R

class WeatherResultsListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_results_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        val locationLatitude = intent.getDoubleExtra("locationLatitude", 0.0)
        val locationLongitude = intent.getDoubleExtra("locationLongitude", 0.0)

        findViewById<FloatingActionButton>(R.id.refresh_fab).setOnClickListener { view ->
            // TODO - refresh the results screen
            Snackbar.make(view, "Latitude : {$locationLatitude} \n Longitude: {$locationLongitude} ", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}