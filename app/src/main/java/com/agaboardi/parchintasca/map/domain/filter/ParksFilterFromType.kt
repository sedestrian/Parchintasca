package com.agaboardi.parchintasca.map.domain.filter

import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.map.domain.model.ParkCategory

class ParksFilterFromType: ParksFilter {
    override fun filter(types: List<ParkCategory>?, parks: List<Park>, query: String?): List<Park> {
        types?.let {types ->
            return parks.filter { park -> match(park.name, query) && types.any { it.value == park.category_id } }
        } ?: return parks.filter { park -> match(park.name, query) }
    }

    fun match(string: String, query: String?): Boolean{
        query?.let {
            return string.toLowerCase().contains(query.toLowerCase())
        }
        return true
    }
}