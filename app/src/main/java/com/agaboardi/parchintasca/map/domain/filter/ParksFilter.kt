package com.agaboardi.parchintasca.map.domain.filter

import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.map.domain.model.ParkCategory

interface ParksFilter {
    fun filter(types: List<ParkCategory>?, parks: List<Park>, query: String?): List<Park>
}