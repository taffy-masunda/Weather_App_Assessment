package za.co.taffy.weatherappassessment.ui

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import za.co.taffy.weatherappassessment.R
import za.co.taffy.weatherappassessment.database.SavedLocation
import za.co.taffy.weatherappassessment.presentation.DatabasePresenter
import java.io.IOException

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap
    lateinit var settingsIcon: ImageView
    lateinit var savedLocationsIcon: ImageView
    lateinit var geocoder: Geocoder
    lateinit var presenter: DatabasePresenter
    lateinit var searchView : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupOnclicks()
        setupMapView()
    }

    fun setupViews(): Unit {
        mapFragment = supportFragmentManager.findFragmentById(R.id.maps_fragment) as SupportMapFragment
        settingsIcon = findViewById(R.id.setting_icon_imageview)
        savedLocationsIcon = findViewById(R.id.saved_locations_icon_imageview)
        searchView = findViewById(R.id.map_searchview)
    }

    private fun setupOnclicks() {
        savedLocationsIcon.setOnClickListener(this)
        settingsIcon.setOnClickListener(this)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                googleMap.clear()

                val location= searchView.query.toString()
                lateinit var addressList : List<Address>

                if(location != ""){
                    geocoder = Geocoder(baseContext)
                     try {
                         addressList = geocoder.getFromLocationName(location, 1)
                         var address = addressList.get(0)

                         var locationLatLong = LatLng(address.latitude, address.longitude)
                         val mapMarker = googleMap.addMarker(
                             MarkerOptions()
                                 .position(locationLatLong)
                                 .title(location)
                                 .snippet("Click for Forecast")
                         )
                         mapMarker.showInfoWindow()
                         googleMap.animateCamera(CameraUpdateFactory.newLatLng(locationLatLong))

                         googleMap.setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener {
                             openWeatherResultsActivity(locationLatLong.latitude, locationLatLong.longitude)
                         })

                         saveLocationToRoomDatabase(location, locationLatLong.latitude, locationLatLong.longitude)

                     }catch (io: IOException){
                         Log.d(constants.TAG, "Error finding address by location name : \n{$io}")
                     }
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
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

    object constants {
        const val TAG = "SEARCH TAG"
    }
}