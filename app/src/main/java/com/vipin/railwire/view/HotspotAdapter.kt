package com.vipin.railwire.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.vipin.railwire.R
import com.vipin.railwire.model.Hotspot
import kotlinx.android.synthetic.main.item_view_layout.view.*
import java.util.*

/**
 * Created by vipin.c on 23/09/2019
 */
class HotspotAdapter(private val clickListener: (Hotspot) -> Unit) :
    RecyclerView.Adapter<HotspotAdapter.ViewHolder>(), Filterable {

    private var mHotspotList: List<Hotspot> = listOf()
    private var mFullList: MutableList<Hotspot> = mutableListOf()
    private var languageCode = 0

    fun setList(list: List<Hotspot>, language: Int) {
        mHotspotList = list
        mFullList.addAll(list)
        languageCode = language
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mHotspotList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mHotspotList[position], clickListener, languageCode)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hotspot: Hotspot, clickListener: (Hotspot) -> Unit, language: Int) {

            if (language == 1) {
                itemView.city_title.text = hotspot.placeName
                itemView.setOnClickListener { clickListener(hotspot) }
            }else{
                itemView.city_title.text = hotspot.placeName_hi
                itemView.setOnClickListener { clickListener(hotspot) }
            }
        }

    }


    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterList: MutableList<Hotspot> = mutableListOf()
                if (constraint.isNullOrEmpty()) {
                    filterList.addAll(mFullList)
                } else {
                    val string = constraint.toString().trim().toLowerCase(Locale.getDefault())
                    for (item in mFullList) {
                        if (item.placeName!!.toLowerCase(Locale.getDefault()).contains(string)) {
                            filterList.add(item)
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mHotspotList = emptyList()
                mHotspotList = ((results?.values as Collection<Hotspot>).toList())
                notifyDataSetChanged()
            }

        }
    }
}