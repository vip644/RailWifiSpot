package com.vipin.railwire.model

import com.google.gson.annotations.SerializedName

/**
 * Created by vipin.c on 23/09/2019
 */
data class RailwireData(

    @SerializedName("hotSpots")
    var hotspot: List<Hotspot>
)