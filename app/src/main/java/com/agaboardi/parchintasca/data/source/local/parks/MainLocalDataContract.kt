package com.agaboardi.parchintasca.data.source.local.parks

import com.agaboardi.parchintasca.data.source.local.db.models.Park
import io.reactivex.Flowable

interface MainLocalDataContract {
    interface ParksLoadedCallback {
        fun onParksLoaded(parks: Flowable<List<Park>>)
        fun onDataNotAvailable()
    }

    interface GetParkCallback {
        fun onParkLoaded(park: Flowable<Park>)
        fun onDataNotAvailable()
    }

    fun loadParks(callback: ParksLoadedCallback)
    fun saveParks(parks: List<Park>)
    fun clearParks()
    fun getPark(parkId: Int, callback: GetParkCallback)
    fun loadFavorites(callback: ParksLoadedCallback)
}