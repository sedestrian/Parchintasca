package com.agaboardi.parchintasca.data.source

import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.data.source.local.parks.MainLocalDataContract
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import mindtek.it.bikeapp.kotlin.extensions.db

class MainRepository(
        val localDataSource: MainLocalDataContract?,
        val remoteDataSource: MainDataSource?
): MainDataSource {
    var cachedParks: MutableList<Park> = mutableListOf()
    var cachedFavorites: MutableList<Int> = mutableListOf()
    var isCacheValid: Boolean = true

    override fun loadParks(callback: MainDataSource.ParksLoadedCallback) {
        if(cachedParks.isNotEmpty() && isCacheValid){
            callback.onParksLoaded(cachedParks)
            return
        }

        if(!isCacheValid){
            getParksFromRemoteDataSource(callback)
        }else{
            localDataSource?.loadParks(object: MainLocalDataContract.ParksLoadedCallback{
                override fun onParksLoaded(parks: Flowable<List<Park>>) {
                    parks.subscribe {
                        if(it.isNotEmpty()) {
                            refreshCache(it)
                            callback.onParksLoaded(it)
                        }else{
                            getParksFromRemoteDataSource(callback)
                        }
                    }
                }

                override fun onDataNotAvailable() {
                    getParksFromRemoteDataSource(callback)
                }
            })
        }
    }

    override fun loadFavorites(callback: MainDataSource.ParksLoadedCallback) {
        if(cachedParks.isNotEmpty() && isCacheValid){
            callback.onParksLoaded(getFavorites(cachedParks))
            return
        }

        if(!isCacheValid){
            getFavoriteParksFromRemoteDataSource(callback)
        }else{
            localDataSource?.loadFavorites(object: MainLocalDataContract.ParksLoadedCallback{
                override fun onParksLoaded(parks: Flowable<List<Park>>) {
                    parks.subscribe {
                        refreshCache(it)
                        callback.onParksLoaded(it)
                    }
                }

                override fun onDataNotAvailable() {
                    getFavoriteParksFromRemoteDataSource(callback)
                }
            })
        }
    }

    override fun getPark(parkId: Int, callback: MainDataSource.GetParkCallback) {
        val cachedPark = getCachedParkFrom(parkId)

        if(cachedPark != null){
            callback.onParkLoaded(cachedPark)
            return
        }
        if(!isCacheValid){
            getParkFromRemoteDataSource(parkId, callback)
        }else{
            localDataSource?.getPark(parkId, object: MainLocalDataContract.GetParkCallback{
                override fun onParkLoaded(park: Flowable<Park>) {
                    park.subscribe {
                        refreshCache(it)
                        callback.onParkLoaded(it)
                    }
                }

                override fun onDataNotAvailable() {
                    getParkFromRemoteDataSource(parkId, callback)
                }
            })
        }
    }

    override fun clearParks() {
        localDataSource?.clearParks()
        remoteDataSource?.clearParks()
    }

    override fun saveParks(parks: List<Park>) {
        remoteDataSource?.saveParks(parks)
        localDataSource?.saveParks(parks)
    }

    fun refreshCache(parks: List<Park>){
        cachedParks.clear()
        cachedParks.addAll(parks)
        isCacheValid = true
    }

    fun refreshCache(park: Park){
        cachedParks.removeAll { it.id == park.id }
        cachedParks.add(park)
        isCacheValid = true
    }

    fun refreshLocalDataSource(parks: List<Park>){
        localDataSource?.clearParks()
        localDataSource?.saveParks(parks)
        isCacheValid = true
    }

    private fun getCachedParkFrom(parkId: Int): Park?{
        return cachedParks.find { it.id == parkId }
    }

    private fun getFavorites(parks: List<Park>): List<Park>{
        if(cachedFavorites.isEmpty())
            cachedFavorites = db.favoritesDao().getFavoriteIds()
        return parks.filter { cachedFavorites.any { fav -> fav == it.id } }
    }

    private fun getParkFromRemoteDataSource(parkId: Int, callback: MainDataSource.GetParkCallback){
        getParksFromRemoteDataSource(object: MainDataSource.ParksLoadedCallback{
            override fun onParksLoaded(parks: List<Park>) {
                val park = parks.find { it.id == parkId }
                park?.let {
                    println("Got from remote" + System.currentTimeMillis())
                    callback.onParkLoaded(it)
                } ?: callback.onDataNotAvailable()
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun getParksFromRemoteDataSource(callback: MainDataSource.ParksLoadedCallback){
        remoteDataSource?.loadParks(object: MainDataSource.ParksLoadedCallback{
            override fun onParksLoaded(parks: List<Park>) {
                refreshCache(parks)
                refreshLocalDataSource(parks)
                callback.onParksLoaded(parks)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun getFavoriteParksFromRemoteDataSource(callback: MainDataSource.ParksLoadedCallback){
        remoteDataSource?.loadParks(object: MainDataSource.ParksLoadedCallback{
            override fun onParksLoaded(parks: List<Park>) {
                refreshCache(parks)
                refreshLocalDataSource(parks)
                callback.onParksLoaded(getFavorites(parks))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }
}