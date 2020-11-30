package za.co.taffy.weatherappassessment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(savedLocation : SavedLocation )

    @Query("SELECT * FROM SavedLocation")
    fun getSavedLocations(): List<SavedLocation>
}