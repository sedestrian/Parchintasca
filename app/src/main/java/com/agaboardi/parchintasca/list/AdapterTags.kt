package com.agaboardi.parchintasca.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.data.source.local.db.models.Park

/**
 * Created by bipol on 3/14/2018.
 */
class AdapterTags(): RecyclerView.Adapter<HolderTag>() {
    var tags: MutableList<String> = mutableListOf()
    private var park: Park? = null
    private var click: (() -> Unit)? = null

    constructor(park: Park): this(){
        this.park = park
        buildTags()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderTag {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_tag, parent, false)
        return HolderTag(view)
    }

    fun setPark(park: Park?){
        this.park = park
        buildTags()
        notifyDataSetChanged()
    }

    private fun buildTags(){
        tags = park?.keywords?.split(delimiters = *charArrayOf('|'))?.toMutableList() ?: mutableListOf()
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: HolderTag, position: Int) {
        holder.tag.text = tags[position]
        holder.itemView.setOnClickListener { click?.invoke() }
    }

    fun setOnClick(click: (() -> Unit)?){
        this.click = click
    }
}