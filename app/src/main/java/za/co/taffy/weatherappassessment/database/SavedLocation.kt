package za.co.taffy.weatherappassessment.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedLocation(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val locationName: String,
    val locationLatitude: Double,
    val locationLongitude: Double)