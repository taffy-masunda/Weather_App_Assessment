package za.co.taffy.weatherappassessment.ui

import android.os.Bundle
import android.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import za.co.taffy.weatherappassessment.R

class SavedLocationsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_locations_list)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}