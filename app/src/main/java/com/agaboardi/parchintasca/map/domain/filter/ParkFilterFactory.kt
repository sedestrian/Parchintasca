package com.agaboardi.parchintasca.map.domain.filter

import java.util.HashMap

class ParkFilterFactory {
    private val mFilters = HashMap<ParkFilterType, ParksFilter>()

    init {
        mFilters[ParkFilterType.FILTER_ALL] = ParksFilterAll()
        mFilters[ParkFilterType.FILTER_TYPE] = ParksFilterFromType()
    }

    fun create(filterType: ParkFilterType?): ParksFilter? {
        return mFilters[filterType]
    }
}