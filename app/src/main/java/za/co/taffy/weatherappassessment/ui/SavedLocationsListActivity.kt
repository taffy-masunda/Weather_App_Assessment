package za.co.taffy.weatherappassessment.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.co.taffy.weatherappassessment.R
import za.co.taffy.weatherappassessment.database.AppDatabase
import za.co.taffy.weatherappassessment.presentation.SavedLocationsPresenter
import za.co.taffy.weatherappassessment.ui.adapters.SavedLocationsAdapter

class SavedLocationsListActivity : AppCompatActivity() {

    lateinit var presenter: SavedLocationsPresenter
    lateinit var noSavedLocationsTextview: TextView
    lateinit var savedLocationsRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_locations_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        setupViews()
        setupRecyclerView()
    }

    private fun setupViews() {
        noSavedLocationsTextview = findViewById(R.id.no_saved_locations_textview)
        savedLocationsRecycler = findViewById(R.id.saved_locations_recyclerview)
    }

    private fun setupRecyclerView() {

        presenter = SavedLocationsPresenter()
        presenter.startDatabase(this)

        savedLocationsRecycler.apply {
            recycledViewPool.apply {
                layoutManager = LinearLayoutManager(context)
                val savedList = AppDatabase.DATABASE_INSTANCE!!.savedLocationDao().getSavedLocations()
                        .sortedByDescending { it.id }
                if(savedList.isNotEmpty()){
                    savedLocationsRecycler.visibility = View.VISIBLE
                    noSavedLocationsTextview.visibility = View.INVISIBLE
                }
                adapter = SavedLocationsAdapter(savedList)
            }
        }
    }
}