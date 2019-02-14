package com.agaboardi.parchintasca.data.source.remote.main

import com.agaboardi.parchintasca.data.source.local.db.models.ParkContainer
import retrofit2.Call
import retrofit2.http.GET



/**
 * Created by bipol on 3/6/2018.
 */
interface GetParksInterface {
    @GET("park/all/")
    fun getParks(): Call<ParkContainer>
}