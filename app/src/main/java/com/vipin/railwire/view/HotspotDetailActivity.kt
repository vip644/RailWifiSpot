package com.vipin.railwire.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vipin.railwire.Constants.Companion.HOTSPOT_DATA
import com.vipin.railwire.Constants.Companion.LANGUAGE_CODE
import com.vipin.railwire.R
import com.vipin.railwire.model.Hotspot
import kotlinx.android.synthetic.main.activity_detail_layout.*

/**
 * Created by vipin.c on 23/09/2019
 */
class HotspotDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var hotspot: Hotspot
    private lateinit var mapView: MapView
    private var languageCode = 0

    companion object {

        fun launchIntent(context: Context, hotspot: Hotspot, language: Int): Intent {
            val intent = Intent(context, HotspotDetailActivity::class.java)
            intent.putExtra(HOTSPOT_DATA, hotspot)
            intent.putExtra(LANGUAGE_CODE, language)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_layout)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        languageCode = intent.getIntExtra(LANGUAGE_CODE, 0)

        hotspot = getHospotData()
        populateView(hotspot)
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun getHospotData(): Hotspot {

        require(intent.hasExtra(HOTSPOT_DATA)) { "$HOTSPOT_DATA must be provided to start this Activity" }
        return intent.getSerializableExtra(HOTSPOT_DATA) as Hotspot
    }

    private fun populateView(hotspot: Hotspot) {

        view_detail_city_ssid.text = hotspot.ssid

        when (languageCode) {

            1 -> {
                view_detail_city_title.text = hotspot.placeName
                view_detail_city_stateName.text = hotspot.stateName
            }

            2 -> {
                view_detail_city_title.text = hotspot.placeName_hi
                view_detail_city_stateName.text = hotspot.stateName_hi
            }

            else -> {
                Toast.makeText(this, "Something wrong with the language!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        if (p0 != null) {
            googleMap = p0
            googleMap.setMinZoomPreference(12F)
            val latLan = LatLng(hotspot.l!![0], hotspot.l!![1])
            googleMap.addMarker(MarkerOptions().position(latLan))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLan))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}