package com.agaboardi.parchintasca.main

import com.claudiodegio.msv.model.Filter

interface FilterInterface {
    fun onFilterAdded(filter: Filter)
    fun onFilterChanged(filters: MutableList<Filter>)
    fun onFilterRemoved(filter: Filter)
}