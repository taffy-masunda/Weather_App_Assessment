package za.co.taffy.weatherappassessment.presentation

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import za.co.taffy.weatherappassessment.database.AppDatabase
import za.co.taffy.weatherappassessment.database.SavedLocation
import za.co.taffy.weatherappassessment.database.SavedLocationDao

class SavedLocationsPresenter {

    lateinit var context: Context
    var database: AppDatabase? = null
    var savedLocationDao: SavedLocationDao? = null
    lateinit var savedList: Observable<List<SavedLocation>>

    fun startDatabase(context: Context) {
        this.context = context
        database = AppDatabase.getAppDataBase(this.context)
    }

    fun getSavedLocations() {
        Observable.fromCallable {
            savedLocationDao = database?.savedLocationDao()

            with(savedLocationDao) {
                return@fromCallable this?.getSavedLocations()
            }

        }.doOnNext { list ->
            savedLocationDao?.getSavedLocations()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}