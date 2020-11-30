package za.co.taffy.weatherappassessment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database( version = 1, entities = [SavedLocation::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedLocationDao(): SavedLocationDao

    companion object {
        var DATABASE_INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (DATABASE_INSTANCE == null){
                synchronized(AppDatabase::class){
                    DATABASE_INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "locationsDB").build()
                }
            }
            return DATABASE_INSTANCE
        }
    }
}