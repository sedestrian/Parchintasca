package com.agaboardi.parchintasca.parkdetail

import android.support.v7.widget.RecyclerView
import android.view.View
import com.agaboardi.parchintasca.common.GlideApp
import kotlinx.android.synthetic.main.characteristic_item.view.*

class CharacteristicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val icon = itemView.icon
    val name = itemView.name

    fun bind(characteristic: Pair<Int, Int>, position: Int){
        GlideApp.with(itemView).load(characteristic.second).into(icon)
        name.text = itemView.context.getString(characteristic.first)
    }
}