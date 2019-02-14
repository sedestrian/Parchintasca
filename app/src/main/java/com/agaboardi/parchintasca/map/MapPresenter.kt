package com.agaboardi.parchintasca.map

import android.os.Handler
import com.agaboardi.parchintasca.common.base.UseCase
import com.agaboardi.parchintasca.common.base.UseCaseHandler
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.map.domain.filter.ParkFilterType
import com.agaboardi.parchintasca.map.domain.model.ParkCategory
import com.agaboardi.parchintasca.map.domain.model.ParkClusterItem
import com.agaboardi.parchintasca.map.domain.usecase.GetPark
import com.agaboardi.parchintasca.map.domain.usecase.GetParks
import com.claudiodegio.msv.model.Filter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.Cluster

class MapPresenter(
        val view: MapContract.View,
        val useCaseHandler: UseCaseHandler,
        val getParks: GetParks,
        val getPark: GetPark
) : MapContract.Presenter {
    private var map: GoogleMap? = null
    private val filterTypes: MutableList<ParkCategory> = mutableListOf()
    private var handler: Handler? = null
    private var currentFilter: ParkFilterType? = ParkFilterType.FILTER_ALL
    private var currentQuery: String? = null
    private val bergamo = LatLngBounds(LatLng(45.656269, 9.589426), LatLng(45.744212, 9.772746))

    init {
        view.setPresenter(this)
    }

    override fun start() {
        view.initializeBottomSheet()
        view.setupTagAdapter()
        view.setupUIInteraction()
        view.initMap()
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        view.setUpClusterer(map)
        loadParks(false)
        view.customizeMap(map)
        view.centerMapOn(map, bergamo, false)
    }

    private fun loadParks(forceUpdate: Boolean) {
        loadParks(forceUpdate, true)
    }

    private fun loadParks(forceUpdate: Boolean, showLoadingUI: Boolean) {
        handler?.removeCallbacksAndMessages(null)
        handler = Handler()
        handler?.postDelayed({
            if (showLoadingUI) {
                view.setLoadingUI(true)
            }

            val requestValues = GetParks.RequestValues(forceUpdate, filterTypes, currentFilter, currentQuery)

            useCaseHandler.execute(getParks, requestValues, object : UseCase.UseCaseCallback<GetParks.ResponseValue> {
                override fun onSuccess(response: GetParks.ResponseValue) {
                    if (showLoadingUI)
                        view.setLoadingUI(false)
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

    override fun onClusterClick(cluster: Cluster<ParkClusterItem>) {
        val builder = LatLngBounds.builder()
        cluster.items.forEach {
            builder.include(it.position)
        }
        map?.let {
            view.centerMapOn(it, builder.build())
        }
    }

    override fun onClusterItemClick(parkClusterItem: ParkClusterItem) {
        loadPark(parkClusterItem.parkId, false)
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

    override fun onParkClick(park: Park) {
        view.openParkDetail(park.id)
    }

    private fun loadPark(parkId: Int, forceUpdate: Boolean) {
        loadPark(parkId, forceUpdate, true)
    }

    private fun loadPark(parkId: Int, forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            view.setLoadingParkUI(true)
        }

        val requestValues = GetPark.RequestValues(parkId)

        useCaseHandler.execute(getPark, requestValues, object : UseCase.UseCaseCallback<GetPark.ResponseValue> {
            override fun onSuccess(response: GetPark.ResponseValue) {
                if (showLoadingUI) {
                    view.setLoadingParkUI(false)
                }
                view.showPark(response.park)
            }

            override fun onError() {
                if (showLoadingUI) {
                    view.setLoadingParkUI(false)
                }
                view.showLoadingError()
            }
        })
    }
}