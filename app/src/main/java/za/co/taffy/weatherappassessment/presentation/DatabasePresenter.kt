package za.co.taffy.weatherappassessment.presentation

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import za.co.taffy.weatherappassessment.database.AppDatabase
import za.co.taffy.weatherappassessment.database.SavedLocation
import za.co.taffy.weatherappassessment.database.SavedLocationDao

class DatabasePresenter {
    lateinit var context: Context
    var database: AppDatabase? = null
    private var savedLocationDao: SavedLocationDao? = null

    fun startDatabase(context: Context) {
        this.context = context
        database = AppDatabase.getAppDataBase(this.context)
    }

    fun saveLocationToDatabase(location: SavedLocation): Unit {
        Observable.fromCallable {
            savedLocationDao = database?.savedLocationDao()

            with(savedLocationDao) {
                this?.insertLocation(location)
            }
        }.doOnNext { list ->
            System.out.print("TEST DISPLAY: /n" + list.toString())
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}