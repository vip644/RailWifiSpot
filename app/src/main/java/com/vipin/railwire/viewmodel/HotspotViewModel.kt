package com.vipin.railwire.viewmodel

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vipin.railwire.model.Hotspot
import com.vipin.railwire.repository.DataRepository
import io.reactivex.Observable

/**
 * Created by vipin.c on 23/09/2019
 */
class HotspotViewModel : ViewModel() {

    fun getHotspotList(): LiveData<List<Hotspot>> {
        return DataRepository.getHotspot()
    }

    fun getSearchView(searchView: SearchView): Observable<String> {
        return DataRepository.fromView(searchView)
    }
}