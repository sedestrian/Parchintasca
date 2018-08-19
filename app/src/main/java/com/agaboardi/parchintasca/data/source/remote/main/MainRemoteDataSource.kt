package com.agaboardi.parchintasca.data.source.remote.main

import com.agaboardi.parchintasca.data.source.MainDataSource
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.data.source.local.db.models.ParkContainer
import com.agaboardi.parchintasca.data.source.remote.ApiManager
import retrofit2.Call
import retrofit2.Response

class MainRemoteDataSource: MainDataSource {
    override fun loadParks(callback: MainDataSource.ParksLoadedCallback) {
        ApiManager.getParks(object: ParksCallback{
            override fun onSuccess(call: Call<ParkContainer>?, response: Response<ParkContainer>?) {
                if(response?.isSuccessful == true) {
                    val parks = response.body()?.records
                    parks?.let {
                        callback.onParksLoaded(it)
                    } ?: callback.onDataNotAvailable()
                }else{
                    callback.onDataNotAvailable()
                }
            }

            override fun onError(call: Call<ParkContainer>?, t: Throwable?) {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun loadFavorites(callback: MainDataSource.ParksLoadedCallback) {
        callback.onDataNotAvailable()
    }

    override fun getPark(parkId: Int, callback: MainDataSource.GetParkCallback) {
        callback.onDataNotAvailable()
    }

    override fun saveParks(parks: List<Park>) {

    }

    override fun clearParks() {

    }

    companion object {
        private var INSTANCE: MainRemoteDataSource? = null

        fun getInstance(): MainRemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = MainRemoteDataSource()
            }
            return INSTANCE!!
        }
    }
}