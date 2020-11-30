package za.co.taffy.weatherappassessment.presentation

import android.content.Context
import za.co.taffy.weatherappassessment.database.AppDatabase
import za.co.taffy.weatherappassessment.database.SavedLocation

class DatabasePresenter {
    var database : AppDatabase? = null

    fun startDatabase(context : Context) {
        database = AppDatabase.getAppDataBase(context)
    }

    fun saveLocationToDatabase(location: SavedLocation): Unit {
        database?.savedLocationDao()?.insertLocation(location)
    }
}