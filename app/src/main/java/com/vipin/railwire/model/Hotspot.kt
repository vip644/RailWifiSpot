package com.vipin.railwire.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by vipin.c on 23/09/2019
 */
data class Hotspot(

    @SerializedName("placeName")
    var placeName: String?,

    @SerializedName("g")
    var g: String?,

    @SerializedName("bssid")
    var bssid: String?,

    @SerializedName("stateName")
    var stateName: String?,

    @SerializedName("stateName_hi")
    var stateName_hi: String?,

    @SerializedName("placeID")
    var placeID: String?,

    @SerializedName("l")
    var l: List<Double>?,

    @SerializedName("placeName_hi")
    var placeName_hi: String?,

    @SerializedName("firebaseKey")
    var firebaseKey: String?,

    @SerializedName("ssid")
    var ssid: String?
) : Serializable