package com.agaboardi.parchintasca.data.source.local.db.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.location.Address

/**
 * Created by bipol on 3/6/2018.
 */
@Entity(tableName = "park")
open class Park(
        @PrimaryKey
        var id: Int = 0,
        var category_id: Int = 0,
        var name: String = "",
        var picture_url: String? = "",
        var opening_time: String? = "",
        var accessibility: Boolean = false,
        var bike_holder: Boolean = false,
        var bike_holder_info: String? = "",
        var fenced: Boolean = false,
        var warded: Boolean = false,
        var warded_info: String? = "",
        var ciclability: Boolean = false,
        var wc: Boolean = false,
        var wc_info: String? = "",
        var playground: Boolean = false,
        var playground_info: String? = "",
        var pets_allowed: Boolean = false,
        var dog_area: Boolean = false,
        var dog_area_info: String? = "",
        var sport_area: Boolean = false,
        var sport_area_info: String? = "",
        var pic_nic_tables: Boolean = false,
        var pic_nic_table_info: String? = "",
        var pic_nic_table_number: Int = 0,
        var drinking_fountain: Boolean = false,
        var drinking_fountain_number: Int = 0,
        var water_elements: Boolean = false,
        var water_elements_info: String? = "",
        var shade: Boolean = false,
        var shade_info: String? = "",
        var roofed: Boolean = false,
        var roofed_info: String? = "",
        var wifi: Boolean = false,
        var old_tree: Boolean = false,
        var opportunities: Boolean = false,
        var opportunities_info: String? = "",
        var landscape: String? = "",
        var peculiarities: String? = "",
        var keywords: String? = "",
        var link: String? = "",
        var icons: String? = "",
        var neighborhood: String? = "",
        var entrances: String? = "",
        var parking: String? = "",
        var directions: String? = "",
        var latitude: Double = 0.0,
        var longitude: Double = 0.0,
        var address: Address? = null,
        @Ignore
        var favorite: Boolean = false
) {}