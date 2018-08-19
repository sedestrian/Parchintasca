package com.agaboardi.parchintasca.map.domain.filter

import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.map.domain.model.ParkCategory

class ParksFilterAll: ParksFilter {
    override fun filter(types: List<ParkCategory>?, parks: List<Park>, query: String?): List<Park> {
        return parks.filter { park -> match(park.name, query) }
    }

    fun match(string: String, query: String?): Boolean{
        query?.let {
            return string.toLowerCase().contains(query.toLowerCase())
        }
        return true
    }
}