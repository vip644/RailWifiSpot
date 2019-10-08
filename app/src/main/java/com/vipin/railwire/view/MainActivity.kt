package com.vipin.railwire.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vipin.railwire.R
import com.vipin.railwire.model.Hotspot
import com.vipin.railwire.viewmodel.HotspotViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var hotspotViewModel: HotspotViewModel
    private lateinit var hotspotAdapter: HotspotAdapter
    private lateinit var mSearchView: SearchView
    private lateinit var mDisposable: CompositeDisposable
    private lateinit var searchDisposable: Disposable
    private var hotspotList: List<Hotspot> = listOf()

    companion object {
        private var language = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDisposable = CompositeDisposable()

        hotspotAdapter = HotspotAdapter {
            startActivity(HotspotDetailActivity.launchIntent(this, it, language))
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = hotspotAdapter

        hotspotViewModel = ViewModelProviders.of(this).get(HotspotViewModel::class.java)

        hotspotViewModel.getHotspotList().observe(this, Observer {
            
            if (hotspotList.isEmpty()) {
                hotspotList = it
                hotspotAdapter.setList(it, language)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        mSearchView = searchItem.actionView as SearchView

        searchDisposable = hotspotViewModel.getSearchView(mSearchView)
            .debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                hotspotAdapter.filter.filter(it)
            }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.English -> {
                refreshData(1)
            }

            R.id.hindi -> {
                refreshData(2)
            }
        }

        return false
    }

    override fun onStop() {
        super.onStop()
        hotspotViewModel.getHotspotList().removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (!searchDisposable.isDisposed) {
            searchDisposable.dispose()
        }
    }

    private fun refreshData(code: Int) {
        if (language != code) {
            recreate()
            language = code
        }
    }
}
