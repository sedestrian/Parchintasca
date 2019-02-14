package com.agaboardi.parchintasca.parkdetail.domain.model

import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.data.source.local.db.models.Park

class ParkDetails(park: Park) {
    val values: MutableList<Pair<Int, ParkDetailEntry>> = mutableListOf()

    init {
        if (park.bike_holder && park.bike_holder_info?.isNotBlank() == true)
            values.add(R.string.bike_holder_info to ParkDetailEntry(park.bike_holder_info))

        if (park.warded && park.warded_info?.isNotBlank() == true)
            values.add(R.string.warded_info to ParkDetailEntry(park.warded_info))

        if (park.wc && park.wc_info?.isNotBlank() == true)
            values.add(R.string.wc_info to ParkDetailEntry(park.wc_info))

        if (park.playground && park.playground_info?.isNotBlank() == true)
            values.add(R.string.playground_info to ParkDetailEntry(park.playground_info))

        if (park.dog_area && park.dog_area_info?.isNotBlank() == true)
            values.add(R.string.dog_area_info to ParkDetailEntry(park.dog_area_info))

        if (park.sport_area && park.sport_area_info?.isNotBlank() == true)
            values.add(R.string.sport_area_info to ParkDetailEntry(park.sport_area_info))

        if (park.pic_nic_tables)
            values.add(R.string.pic_nic_tables_info to ParkDetailEntry(park.pic_nic_table_info, park.pic_nic_table_number))

        if (park.drinking_fountain)
            values.add(R.string.drinking_fountain_info to ParkDetailEntry(null, park.drinking_fountain_number))

        if (park.water_elements && park.water_elements_info?.isNotBlank() == true)
            values.add(R.string.water_elements_info to ParkDetailEntry(park.water_elements_info))

        if (park.shade && park.shade_info?.isNotBlank() == true)
            values.add(R.string.shade_info to ParkDetailEntry(park.shade_info))

        if (park.roofed && park.roofed_info?.isNotBlank() == true)
            values.add(R.string.roofed_info to ParkDetailEntry(park.roofed_info))

        if (park.opportunities && park.opportunities_info?.isNotBlank() == true)
            values.add(R.string.opportunities_info to ParkDetailEntry(park.opportunities_info))

        if (park.landscape != null && park.landscape!!.isNotBlank())
            values.add(R.string.landscape to ParkDetailEntry(park.landscape))

        if (park.peculiarities != null && park.peculiarities!!.isNotBlank())
            values.add(R.string.peculiarities to ParkDetailEntry(park.peculiarities))

        if (park.neighborhood != null && park.neighborhood!!.isNotBlank())
            values.add(R.string.neighborhood to ParkDetailEntry(park.neighborhood))

        if (park.entrances != null && park.entrances!!.isNotBlank())
            values.add(R.string.entrances to ParkDetailEntry(park.entrances?.replace('|', ',')))

        if (park.parking != null && park.parking!!.isNotBlank())
            values.add(R.string.parking to ParkDetailEntry(park.parking?.replace('|', ',')))
    }
}