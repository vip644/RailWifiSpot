package com.vipin.railwire.repository

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import com.vipin.railwire.model.Hotspot
import com.vipin.railwire.model.RailwireData
import com.vipin.railwire.model.request.RequestGenerator
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by vipin.c on 23/09/2019
 */
object DataRepository {

    private var liveData = MutableLiveData<List<Hotspot>>()
    private var raildata: List<Hotspot> = listOf()

    fun getHotspot(): MutableLiveData<List<Hotspot>> {

        RequestGenerator.create().getHotspot()
            .enqueue(object : Callback<RailwireData> {
                override fun onFailure(call: Call<RailwireData>, t: Throwable) {
                    Log.e("DataRepository", t.message)
                }

                override fun onResponse(
                    call: Call<RailwireData>,
                    response: Response<RailwireData>
                ) {
                    val railwireData = response.body()
                    if (railwireData?.hotspot != null) {
                        raildata = railwireData.hotspot
                        liveData.value = raildata
                    }
                }

            })
        return liveData
    }

    fun fromView(searchView: SearchView): Observable<String> {

        val subject: PublishSubject<String> = PublishSubject.create()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                subject.onComplete()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { subject.onNext(it) }
                return true
            }
        })
        return subject
    }
}
