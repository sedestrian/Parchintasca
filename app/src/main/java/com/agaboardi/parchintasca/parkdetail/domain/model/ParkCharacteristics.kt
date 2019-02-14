package com.agaboardi.parchintasca.parkdetail.domain.model

import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.data.source.local.db.models.Park

class ParkCharacteristics(park: Park) {
    val values: MutableList<Pair<Int, Int>> = mutableListOf()

    init {
        if (park.accessibility)
            values.add(R.string.accessibility to R.drawable.accessibility)
        if (park.bike_holder)
            values.add(R.string.bike_holder to R.drawable.bike_holder)
        if (park.fenced)
            values.add(R.string.fenced to R.drawable.fenced)
        if (park.warded)
            values.add(R.string.warded to R.drawable.warded)
        if (park.ciclability)
            values.add(R.string.ciclability to R.drawable.ciclability)
        if (park.wc)
            values.add(R.string.wc to R.drawable.wc)
        if (park.playground)
            values.add(R.string.playground to R.drawable.playground)
        if (park.pets_allowed)
            values.add(R.string.pets_allowed to R.drawable.pets_allowed)
        if (park.dog_area)
            values.add(R.string.dog_area to R.drawable.dog_area)
        if (park.sport_area)
            values.add(R.string.sport_area to R.drawable.sport_area)
        if (park.pic_nic_tables)
            values.add(R.string.pic_nic_tables to R.drawable.pic_nic_tables)
        if (park.drinking_fountain)
            values.add(R.string.drinking_fountain to R.drawable.drinking_fountain)
        if (park.water_elements)
            values.add(R.string.water_elements to R.drawable.water_elements)
        if (park.shade)
            values.add(R.string.shade to R.drawable.shade)
        if (park.roofed)
            values.add(R.string.roofed to R.drawable.roofed)
        if (park.wifi)
            values.add(R.string.wifi to R.drawable.wifi)
        if (park.old_tree)
            values.add(R.string.old_tree to R.drawable.old_tree)
        if (park.opportunities)
            values.add(R.string.opportunities to R.drawable.opportunities)
    }
}