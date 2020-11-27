package za.co.taffy.weatherappassessment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import za.co.taffy.weatherappassessment.R

class MainActivity : AppCompatActivity() {

    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupMapView()
    }

    fun setupMapView() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.maps_fragment) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            googleMap.setOnMapClickListener { it ->
                googleMap.clear()
                val mapMarker = googleMap.addMarker(
                    MarkerOptions()
                        .position(it)
                        .title("Click Here")
                        .snippet("For Weather Information")
                )
                mapMarker.showInfoWindow()
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(it))

                googleMap.setOnInfoWindowClickListener { this }
                // TODO Show name adn list of days  for weather for that location
                // TODO save the selected location name and it's lat-long

            }
        })

    }

    internal inner class InfoWindowActivity : AppCompatActivity(),
        GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback {
        override fun onMapReady(googleMap: GoogleMap) {
            googleMap.setOnInfoWindowClickListener(this)
        }

        override fun onInfoWindowClick(marker: Marker) {
            Toast.makeText(
                this, "Info window clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
