package com.agaboardi.parchintasca.list

import android.os.Handler
import com.agaboardi.parchintasca.common.base.UseCase
import com.agaboardi.parchintasca.common.base.UseCaseHandler
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.map.domain.filter.ParkFilterType
import com.agaboardi.parchintasca.map.domain.model.ParkCategory
import com.agaboardi.parchintasca.map.domain.usecase.GetParks
import com.claudiodegio.msv.model.Filter

class ListPresenter(
        val view: ListContract.View,
        val useCaseHandler: UseCaseHandler,
        val getParks: GetParks
): ListContract.Presenter {
    private val filterTypes: MutableList<ParkCategory> = mutableListOf()
    private var handler: Handler? = null
    private var currentFilter: ParkFilterType? = ParkFilterType.FILTER_ALL
    private var currentQuery: String? = null

    init {
        view.setPresenter(this)
    }

    override fun start() {
        view.setupList()
        loadParks(false)
    }

    override fun loadParks(forceUpdate: Boolean) {
        loadParks(forceUpdate, true)
    }

    override fun loadParks(forceUpdate: Boolean, showLoadingUI: Boolean) {
        handler?.removeCallbacksAndMessages(null)
        handler = Handler()
        handler?.postDelayed({
            println("Plus one")
            if (showLoadingUI) {
                view.setLoadingUI(true)
            }

            val requestValues = GetParks.RequestValues(forceUpdate, filterTypes, currentFilter, currentQuery)

            useCaseHandler.execute(getParks, requestValues, object : UseCase.UseCaseCallback<GetParks.ResponseValue> {
                override fun onSuccess(response: GetParks.ResponseValue) {
                    println("Here: " + response.parks.size)
                    if (showLoadingUI)
                        view.setLoadingUI(false)
                    if (response.parks.isNotEmpty())
                        view.hideEmptyParks()
                    else
                        view.showEmptyParks()
                    view.showParks(response.parks)
                }

                override fun onError() {
                    if (showLoadingUI)
                        view.setLoadingUI(false)
                    view.showLoadingError()
                }
            })
        }, 50)
    }

    override fun onParkClick(park: Park, position: Int) {
        view.goToPark(park)
    }

    override fun onFilterAdded(filter: Filter) {
        filterTypes.add(ParkCategory.from(filter.id))
        currentFilter = ParkFilterType.FILTER_TYPE
        loadParks(false, false)
    }

    override fun onFilterChanged(filters: MutableList<Filter>) {
        /*filterTypes.clear()
        filterTypes.addAll(filters.map { ParkCategory.from(it.id) })*/
        currentFilter = if(filterTypes.isEmpty())
            ParkFilterType.FILTER_ALL
        else
            ParkFilterType.FILTER_TYPE
    }

    override fun onFilterRemoved(filter: Filter) {
        filterTypes.removeAll { it.value == filter.id }
        loadParks(false, false)
        currentFilter = if(filterTypes.isEmpty())
            ParkFilterType.FILTER_ALL
        else
            ParkFilterType.FILTER_TYPE
    }

    override fun onQueryTextChanged(newText: String?) {
        currentQuery = newText
        loadParks(false, false)
    }

    override fun onQueryTextSubmit(query: String?) {

    }

    override fun onSearchViewClosed() {
        filterTypes.clear()
        currentFilter = ParkFilterType.FILTER_ALL
        currentQuery = null
        loadParks(false, false)
    }

    override fun onSearchViewShown() {

    }
}