package com.agaboardi.parchintasca.data.source

import android.arch.lifecycle.LiveData
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import io.reactivex.Observable

interface MainDataSource {
    interface ParksLoadedCallback{
        fun onParksLoaded(parks: List<Park>)
        fun onDataNotAvailable()
    }

    interface GetParkCallback{
        fun onParkLoaded(park: Park)
        fun onDataNotAvailable()
    }

    fun loadParks(callback: ParksLoadedCallback)
    fun saveParks(parks: List<Park>)
    fun clearParks()
    fun getPark(parkId: Int, callback: GetParkCallback)
    fun loadFavorites(callback: ParksLoadedCallback)
}