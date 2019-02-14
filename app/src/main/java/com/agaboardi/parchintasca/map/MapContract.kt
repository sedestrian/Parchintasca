package com.agaboardi.parchintasca.map

import com.agaboardi.parchintasca.common.base.BasePresenter
import com.agaboardi.parchintasca.common.base.BaseView
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.map.domain.model.ParkClusterItem
import com.claudiodegio.msv.model.Filter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.Cluster

interface MapContract {
    interface View: BaseView<Presenter>{

        fun initMap()
        fun customizeMap(map: GoogleMap)
        fun setUpClusterer(map: GoogleMap)
        fun centerMapOn(map: GoogleMap, location: LatLng, zoom: Float = 16f, animated: Boolean = true)
        fun centerMapOn(map: GoogleMap, bounds: LatLngBounds, animated: Boolean = true)
        fun setLoadingUI(visible: Boolean)
        fun showLoadingError()
        fun showParks(parks: List<Park>)
        fun setLoadingParkUI(visible: Boolean)
        fun showPark(park: Park)
        fun initializeBottomSheet()
        fun setupUIInteraction()
        fun setupTagAdapter()
        fun openParkDetail(id: Int)
    }

    interface Presenter: BasePresenter{
        fun onMapReady(map: GoogleMap)
        fun onClusterItemClick(parkClusterItem: ParkClusterItem)
        fun onClusterClick(cluster: Cluster<ParkClusterItem>)
        fun onFilterAdded(filter: Filter)
        fun onFilterChanged(filters: MutableList<Filter>)
        fun onFilterRemoved(filter: Filter)
        fun onQueryTextChanged(newText: String?)
        fun onQueryTextSubmit(query: String?)
        fun onSearchViewClosed()
        fun onSearchViewShown()
        fun onParkClick(park: Park)
    }
}