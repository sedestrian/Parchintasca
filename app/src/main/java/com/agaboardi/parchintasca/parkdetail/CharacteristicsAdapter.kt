package com.agaboardi.parchintasca.parkdetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.agaboardi.parchintasca.R

class CharacteristicsAdapter: RecyclerView.Adapter<CharacteristicsViewHolder>() {
    private var characteristics: MutableList<Pair<Int, Int>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CharacteristicsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.characteristic_item, parent, false)
        return CharacteristicsViewHolder(view)
    }

    override fun getItemCount(): Int = characteristics.size

    override fun onBindViewHolder(holder: CharacteristicsViewHolder, position: Int) {
        holder.bind(characteristics[position], position)
    }

    fun setCharacteristics(characteristics: MutableList<Pair<Int, Int>>){
        this.characteristics = characteristics
        notifyDataSetChanged()
    }
}