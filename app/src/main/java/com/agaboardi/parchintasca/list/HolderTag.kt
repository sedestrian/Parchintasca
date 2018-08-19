package com.agaboardi.parchintasca.list

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_tag.view.*

/**
 * Created by bipol on 3/14/2018.
 */
class HolderTag(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tag = itemView.tagName
}