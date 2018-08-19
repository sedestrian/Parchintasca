package com.agaboardi.parchintasca.parkdetail

import android.support.v7.widget.RecyclerView
import android.view.View
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.parkdetail.domain.model.ParkDetailEntry
import kotlinx.android.synthetic.main.detail_item.view.*
import mindtek.it.bikeapp.kotlin.extensions.setGone
import mindtek.it.bikeapp.kotlin.extensions.setVisible

class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name = itemView.name
    val text = itemView.info
    val number = itemView.number

    fun bind(detail: Pair<Int, ParkDetailEntry>, position: Int) {
        name.text = itemView.context.getString(detail.first)
        text.text = detail.second.data
        if (detail.second.number != null) {
            if (detail.second.data == null) {
                number.setGone()
                text.text = detail.second.number.toString()
            } else {
                number.setVisible()
                number.text = String.format(itemView.context.getString(R.string.number_info), detail.second.number)
            }
        } else {
            number.setGone()
        }
    }
}