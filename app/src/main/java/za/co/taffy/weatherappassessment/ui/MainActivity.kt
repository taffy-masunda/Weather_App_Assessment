package za.co.taffy.weatherappassessment.ui

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import za.co.taffy.weatherappassessment.R
import za.co.taffy.weatherappassessment.database.SavedLocation
import za.co.taffy.weatherappassessment.presentation.DatabasePresenter

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap
    lateinit var settingsIcon: ImageView
    lateinit var savedLocationsIcon: ImageView
    lateinit var geocoder: Geocoder
    lateinit var presenter: DatabasePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupOnclicks()
        setupMapView()
    }

    fun setupViews(): Unit {
        mapFragment =
            supportFragmentManager.findFragmentById(R.id.maps_fragment) as SupportMapFragment
        settingsIcon = findViewById(R.id.setting_icon_imageview)
        savedLocationsIcon = findViewById(R.id.saved_locations_icon_imageview)
    }

    private fun setupOnclicks() {
        savedLocationsIcon.setOnClickListener(this)
        settingsIcon.setOnClickListener(this)
    }

    fun setupMapView() {

        var locationName: String
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            var latitude: Double? = null
            var longitude: Double? = null

            googleMap.setOnMapClickListener { it ->
                googleMap.clear()
                latitude = it.latitude
                longitude = it.longitude
                locationName = getLocationNameByLatLong(latitude!!, longitude!!)
                val mapMarker = googleMap.addMarker(
                    MarkerOptions()
                        .position(it)
                        .title(locationName)
                        .snippet("Click for Forecast")
                )
                mapMarker.showInfoWindow()
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(it))

                googleMap.setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener {
                    openWeatherResultsActivity(latitude!!, longitude!!)
                })

                saveLocationToRoomDatabase(locationName, it.latitude, it.longitude)
            }
        })

    }

    private fun openWeatherResultsActivity(latitude: Double, longitude: Double) {
        val openSavedWeatherResultsIntent = Intent(this, WeatherResultsListActivity::class.java)
        openSavedWeatherResultsIntent.putExtra("locationLatitude", latitude)
        openSavedWeatherResultsIntent.putExtra("locationLongitude", longitude)
        openSavedWeatherResultsIntent.putExtra("locationName", getLocationNameByLatLong(latitude, longitude))

        startActivity(openSavedWeatherResultsIntent)
    }

    private fun getLocationNameByLatLong(latitude: Double, longitude: Double): String {
        geocoder = Geocoder(this)
        val address = geocoder.getFromLocation(latitude, longitude, 1)
        return address[0].getAddressLine(0)
    }

    private fun saveLocationToRoomDatabase(
        name: String,
        latitude: Double,
        longitude: Double
    ): Unit {
        presenter = DatabasePresenter()
        presenter.startDatabase(this)
        presenter.saveLocationToDatabase(SavedLocation(0, name, latitude, longitude))
        Toast.makeText(this, "Location saved to history", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.saved_locations_icon_imageview -> {
                val openSavedLocationsActivityIntent =
                    Intent(this, SavedLocationsListActivity::class.java).apply {}
                startActivity(openSavedLocationsActivityIntent)
            }

            R.id.setting_icon_imageview -> {
                val openSettingsActivityIntent = Intent(this, SettingsActivity::class.java).apply {}
                startActivity(openSettingsActivityIntent)
            }
        }
    }

}