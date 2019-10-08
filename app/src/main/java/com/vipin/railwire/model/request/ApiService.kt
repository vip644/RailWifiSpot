package com.vipin.railwire.model.request

import com.vipin.railwire.model.RailwireData
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by vipin.c on 23/09/2019
 */
interface ApiService {

    @GET("railwire.json")
    fun getHotspot(): Call<RailwireData>
}