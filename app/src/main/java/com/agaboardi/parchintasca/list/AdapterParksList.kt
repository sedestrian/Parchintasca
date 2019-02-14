package com.agaboardi.parchintasca.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.data.source.local.db.models.Park

/**
 * Created by bipol on 3/13/2018.
 */
class AdapterParksList(val onItemClick: ((park: Park, position: Int) -> Unit)) : RecyclerView.Adapter<HolderParkList>() {
    var parks: MutableList<Park> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderParkList {
        val inflater = LayoutInflater.from(parent.context)
        return HolderParkList(inflater.inflate(R.layout.item_park, parent, false))
    }

    override fun getItemCount(): Int = parks.size

    override fun onBindViewHolder(holder: HolderParkList, position: Int) {
        holder.bind(position, parks[position], onItemClick)
    }

    fun replaceData(parks: List<Park>){
        setList(parks)
        notifyDataSetChanged()
    }

    private fun setList(parks: List<Park>) {
        this.parks = parks.toMutableList()
    }

    fun getData(): List<Park> {
        return parks
    }
}