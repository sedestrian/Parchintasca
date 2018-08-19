package com.agaboardi.parchintasca

import android.app.Application
import android.arch.persistence.room.Room
import android.location.Geocoder
import com.agaboardi.parchintasca.data.source.local.db.AppDatabase
import java.util.*

/**
 * Created by bipol on 3/5/2018.
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()

        initRoom()
        initGeocoder()
    }

    private fun initGeocoder(){
        geocoder = Geocoder(this, Locale.getDefault())
    }

    private fun initRoom() {
        val room = Room.
                databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java,
                        getString(R.string.database_name)
                )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        AppDatabase.setInstance(room)
    }


    companion object {
        var geocoder: Geocoder? = null
    }
}