package com.agaboardi.parchintasca.map


import android.content.Intent
import android.os.Bundle
import android.support.design.card.MaterialCardView
import android.support.design.shape.MaterialShapeDrawable
import android.support.design.shape.ShapePath
import android.support.design.shape.ShapePathModel
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.common.GlideApp
import com.agaboardi.parchintasca.common.extensions.*
import com.agaboardi.parchintasca.common.injection.Injection
import com.agaboardi.parchintasca.data.source.local.db.models.ModelFavorite
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.list.AdapterTags
import com.agaboardi.parchintasca.main.SearchContract
import com.agaboardi.parchintasca.map.domain.model.ParkCategory
import com.agaboardi.parchintasca.map.domain.model.ParkClusterItem
import com.agaboardi.parchintasca.parkdetail.ActivityParkDetail
import com.claudiodegio.msv.model.Filter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.clustering.ClusterManager
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.fragment_map.*
import mindtek.it.bikeapp.kotlin.extensions.db
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast

class FragmentMap : Fragment(), MapContract.View, SearchContract.Subscriber {
    private var presenter: MapContract.Presenter? = null
    private var clusterManager: ClusterManager<ParkClusterItem>? = null
    private var bottomSheet: BottomSheetBehavior<MaterialCardView>? = null
    private var tagsAdapter: AdapterTags? = null

    override fun setPresenter(presenter: MapContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map?.onCreate(savedInstanceState)

        setupSearchContract()

        MapPresenter(this,
                Injection.provideUseCaseHandler(),
                Injection.provideGetParks(),
                Injection.provideGetPark())

        presenter?.start()

    }

    override fun initializeBottomSheet(){
        bottomSheet = BottomSheetBehavior.from(sheet)
        bottomSheet?.hide()
        bottomSheet?.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                rotateIcon(slideOffset)
            }

            override fun onStateChanged(p0: View, p1: Int) {

            }
        })
    }

    override fun setupTagAdapter() {
        tags.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        tagsAdapter = AdapterTags()
        tags.adapter = tagsAdapter
    }

    override fun setupUIInteraction(){
        header.setOnClickListener {
            if (bottomSheet?.isExpanded() == true)
                bottomSheet?.collapse()
            else if (bottomSheet?.isCollapsed() == true)
                bottomSheet?.expand()
        }
    }

    private fun rotateIcon(percent: Float) {
        if (percent >= 0)
            chevron.rotation = percent * (-180)
    }

    private fun setupSearchContract() {
        val host = activity as? SearchContract.Host
        host?.addSubscriber(this)
    }

    override fun initMap() {
        map?.getMapAsync { presenter?.onMapReady(it) }
    }

    override fun customizeMap(map: GoogleMap) {
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
        map.uiSettings.isIndoorLevelPickerEnabled = false
        map.uiSettings.isMapToolbarEnabled = false
        map.uiSettings.isZoomControlsEnabled = false
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.map))
    }

    override fun setUpClusterer(map: GoogleMap) {
        clusterManager = ClusterManager(activity, map)
        clusterManager?.setOnClusterItemClickListener { parkClusterItem ->
            presenter?.onClusterItemClick(parkClusterItem)
            true
        }
        clusterManager?.setOnClusterClickListener { cluster ->
            presenter?.onClusterClick(cluster)
            true
        }
        clusterManager?.renderer = ParksClusterRenderer(activity!!, map, clusterManager!!)
        map.setOnCameraIdleListener(clusterManager)
        map.setOnMarkerClickListener(clusterManager)
    }

    override fun centerMapOn(map: GoogleMap, location: LatLng, zoom: Float, animated: Boolean) {
        if (animated)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom))
        else
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom))
    }

    override fun centerMapOn(map: GoogleMap, bounds: LatLngBounds, animated: Boolean) {
        if (animated)
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 60.dp))
        else
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 60.dp))
    }

    override fun showParks(parks: List<Park>) {
        clusterManager?.clearItems()
        parks.forEach { park ->
            val icon = ParkCategory.drawableFor(park.category_id)

            val marker = ParkClusterItem(
                    LatLng(park.latitude, park.longitude),
                    icon,
                    park.id
            )
            clusterManager?.addItem(marker)
        }
        clusterManager?.cluster()
    }

    override fun onFilterAdded(filter: Filter) {
        presenter?.onFilterAdded(filter)
    }

    override fun onFilterChanged(filters: MutableList<Filter>) {
        presenter?.onFilterChanged(filters)
    }

    override fun onFilterRemoved(filter: Filter) {
        presenter?.onFilterRemoved(filter)
    }

    override fun onQueryTextChange(newText: String?) {
        presenter?.onQueryTextChanged(newText)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter?.onQueryTextSubmit(query)
        return false
    }

    override fun onSearchViewClosed() {
        presenter?.onSearchViewClosed()
    }

    override fun onSearchViewShown() {
        presenter?.onSearchViewShown()
    }

    override fun showPark(park: Park) {
        name.text = park.name
        address.text = park.address?.getAddressLine(0)
        type.text = getString(ParkCategory.named(park.category_id))
        favorite.isLiked = db.parkDao().isFavorite(park.id)
        tagsAdapter?.setPark(park)
        GlideApp.with(this).load(ParkCategory.drawableFor(park.category_id)).into(typeIconHeader)
        if(bottomSheet?.isHidden() == true)
            bottomSheet?.collapse()
        sheet.setOnClickListener { presenter?.onParkClick(park) }
        tagsAdapter?.setOnClick {
            println("Tags clicked")
            presenter?.onParkClick(park)
        }
        favorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(p0: LikeButton?) {
                db.parkDao().setFavorite(ModelFavorite(park.id, true))
                park.favorite = true
                db.parkDao().savePark(park)
            }

            override fun unLiked(p0: LikeButton?) {
                db.parkDao().removeFavorite(park.id)
                park.favorite = false
                db.parkDao().savePark(park)
            }
        })
    }

    override fun openParkDetail(id: Int) {
        val intent = Intent(activity, ActivityParkDetail::class.java)
        intent.putExtra(ActivityParkDetail.PARK_ID, id)
        startActivity(intent)
    }

    override fun setLoadingParkUI(visible: Boolean) {

    }

    override fun showLoadingError() {

    }

    override fun setLoadingUI(visible: Boolean) {

    }

    override fun onStart() {
        super.onStart()
        map?.onStart()
    }

    override fun onStop() {
        super.onStop()
        map?.onStop()
    }

    override fun onResume() {
        super.onResume()
        map?.onResume()
    }

    override fun onPause() {
        super.onPause()
        map?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        map?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map?.onSaveInstanceState(outState)
    }

    companion object {
        fun newInstance() = FragmentMap()
    }

}
