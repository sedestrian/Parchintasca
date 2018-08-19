package com.agaboardi.parchintasca.data.source.local.parks

import com.agaboardi.parchintasca.common.base.AppExecutors
import com.agaboardi.parchintasca.data.source.local.db.daos.DaoPark
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import io.reactivex.Flowable

class MainLocalDataSource(private val appExecutors: AppExecutors, private val parksDao: DaoPark?) : MainLocalDataContract {
    override fun loadParks(callback: MainLocalDataContract.ParksLoadedCallback) {
        val runnable = Runnable {
            val parks = parksDao?.getParksFlowable()

            appExecutors.mainThread().execute {
                parks?.let {flowable ->
                    callback.onParksLoaded(flowable)
                } ?: callback.onDataNotAvailable()
            }
        }

        appExecutors.diskIO().execute(runnable)
    }

    override fun loadFavorites(callback: MainLocalDataContract.ParksLoadedCallback) {
        val runnable = Runnable {
            val parks = parksDao?.getFavoritesFlowable()

            appExecutors.mainThread().execute {
                parks?.let {
                    callback.onParksLoaded(it)
                } ?: callback.onDataNotAvailable()
            }
        }

        appExecutors.diskIO().execute(runnable)
    }

    override fun getPark(parkId: Int, callback: MainLocalDataContract.GetParkCallback) {
        val runnable = Runnable {
            val park = parksDao?.getParkById(parkId)

            appExecutors.mainThread().execute {
                park?.let {
                    callback.onParkLoaded(it)
                } ?: callback.onDataNotAvailable()
            }
        }

        appExecutors.diskIO().execute(runnable)
    }

    override fun saveParks(parks: List<Park>) {
        val saveRunnable = Runnable {
            parksDao?.saveParks(parks.toMutableList())
        }
        appExecutors.diskIO().execute(saveRunnable)
    }

    override fun clearParks() {
        val clearParks = Runnable {
            parksDao?.clear()
        }
        appExecutors.diskIO().execute(clearParks)
    }

    companion object {
        var INSTANCE: MainLocalDataSource? = null

        fun getInstance(appExecutors: AppExecutors, parksDao: DaoPark?): MainLocalDataSource? {
            if (INSTANCE == null) {
                synchronized(MainLocalDataSource::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MainLocalDataSource(appExecutors, parksDao)
                    }
                }
            }
            return INSTANCE
        }
    }
}