package com.agaboardi.parchintasca.list

import com.agaboardi.parchintasca.common.base.BasePresenter
import com.agaboardi.parchintasca.common.base.BaseView
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.claudiodegio.msv.model.Filter

interface ListContract{
    interface View: BaseView<Presenter>{

        fun setupList()
        fun showParks(parks: List<Park>)
        fun setLoadingUI(visible: Boolean)
        fun showLoadingError()
        fun hideEmptyParks()
        fun showEmptyParks()
        fun goToPark(park: Park)
    }

    interface Presenter: BasePresenter{
        fun onParkClick(park: Park, position: Int)

        fun onFilterAdded(filter: Filter)
        fun onFilterChanged(filters: MutableList<Filter>)
        fun onFilterRemoved(filter: Filter)
        fun onQueryTextChanged(newText: String?)
        fun onQueryTextSubmit(query: String?)
        fun onSearchViewClosed()
        fun onSearchViewShown()
        fun loadParks(forceUpdate: Boolean)
        fun loadParks(forceUpdate: Boolean, showLoadingUI: Boolean)
    }
}