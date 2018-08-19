package com.agaboardi.parchintasca.data.source.remote.main

import com.agaboardi.parchintasca.common.base.ApiCallback
import com.agaboardi.parchintasca.data.source.local.db.models.ParkContainer
import mindtek.it.bikeapp.kotlin.extensions.db
import mindtek.it.bikeapp.kotlin.extensions.geocoder
import mindtek.it.bikeapp.kotlin.extensions.uiThread
import retrofit2.Call
import retrofit2.Response

/**
 * Created by bipol on 3/6/2018.
 */
interface ParksCallback : ApiCallback<ParkContainer> {
    override fun onFailure(call: Call<ParkContainer>?, t: Throwable?) {
        uiThread {
            println(t?.localizedMessage)
            onError(call, t)
        }
    }

    override fun onResponse(call: Call<ParkContainer>?, response: Response<ParkContainer>?) {
        response?.body()?.let {resp ->
            geocoder?.let {geocode ->
                resp.records.forEach { park ->
                    val location = geocode.getFromLocation(park.latitude, park.longitude, 1)
                    park.address = location.firstOrNull()
                }
            }
            db.parkDao().saveParks(resp.records)
        }
        uiThread {
            onSuccess(call, response)
        }
    }
}