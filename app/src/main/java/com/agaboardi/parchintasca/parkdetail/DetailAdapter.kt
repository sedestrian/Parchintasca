package com.agaboardi.parchintasca.parkdetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.parkdetail.domain.model.ParkDetailEntry

class DetailAdapter: RecyclerView.Adapter<DetailViewHolder>() {
    private var details: MutableList<Pair<Int, ParkDetailEntry>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_item, parent, false)
        return DetailViewHolder(view)
    }

    override fun getItemCount(): Int = details.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(details[position], position)
    }

    fun setDetails(details: MutableList<Pair<Int, ParkDetailEntry>>){
        this.details = details
        notifyDataSetChanged()
    }
}