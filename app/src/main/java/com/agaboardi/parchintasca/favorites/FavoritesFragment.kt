package com.agaboardi.parchintasca.favorites


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.common.GlideApp
import com.agaboardi.parchintasca.common.SpacingItemDecorator
import com.agaboardi.parchintasca.common.extensions.dp
import com.agaboardi.parchintasca.common.injection.Injection
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.list.AdapterParksList
import com.agaboardi.parchintasca.list.ListContract
import com.agaboardi.parchintasca.list.ListPresenter
import com.agaboardi.parchintasca.main.SearchContract
import com.agaboardi.parchintasca.parkdetail.ActivityParkDetail
import com.claudiodegio.msv.model.Filter
import kotlinx.android.synthetic.main.fragment_list.*
import mindtek.it.bikeapp.kotlin.extensions.setGone
import mindtek.it.bikeapp.kotlin.extensions.setVisible
import org.jetbrains.anko.support.v4.dip

class FavoritesFragment : Fragment(), FavoritesContract.View, SearchContract.Subscriber {
    private var presenter: FavoritesContract.Presenter? = null
    private var adapter: AdapterParksList? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchContract()

        FavoritesPresenter(
                this,
                Injection.provideUseCaseHandler(),
                Injection.provideGetFavorites()
        )

        presenter?.start()
        setupUiInteraction()
    }

    override fun goToPark(park: Park) {
        val intent = Intent(activity, ActivityParkDetail::class.java)
        intent.putExtra(ActivityParkDetail.PARK_ID, park.id)
        startActivity(intent)
    }

    private fun setupSearchContract() {
        val host = activity as? SearchContract.Host
        host?.addSubscriber(this)
    }

    private fun setupUiInteraction(){
        refresh.setOnRefreshListener { presenter?.loadParks(true) }
    }

    override fun setupList(){
        adapter = AdapterParksList { park, position ->
            presenter?.onParkClick(park, position)
        }
        parks.layoutManager = LinearLayoutManager(activity)
        parks.addItemDecoration(SpacingItemDecorator(dip(8)))
        parks.adapter = adapter
    }

    override fun setLoadingUI(visible: Boolean) {
        refresh.isRefreshing = visible
    }

    override fun showLoadingError() {

    }

    override fun showParks(parks: List<Park>){
        adapter?.replaceData(parks)
    }

    override fun showEmptyFavorites() {
        GlideApp.with(this).load(R.drawable.empty_favorites).into(emptyImage)
        emptyTitle.text = getText(R.string.no_favorites)
        emptyImage.setVisible()
        emptyTitle.setVisible()
    }

    override fun hideEmptyFavorites(){
        emptyImage.setGone()
        emptyTitle.setGone()
    }

    override fun setPresenter(presenter: FavoritesContract.Presenter) {
        this.presenter = presenter
    }

    private fun addPadding(){
        TransitionManager.beginDelayedTransition(root)
        root.setPadding(0, dip(100), 0, 0)
    }

    private fun clearPadding(){
        TransitionManager.beginDelayedTransition(root)
        root.setPadding(0 , dip(56), 0, 0)
    }

    override fun onSearchViewClosed() {
        clearPadding()
        presenter?.onSearchViewClosed()
    }

    override fun onSearchViewShown() {
        addPadding()
        presenter?.onSearchViewShown()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter?.onQueryTextSubmit(query)
        return true
    }

    override fun onQueryTextChange(newText: String?) {
        presenter?.onQueryTextChanged(newText)
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


    companion object {
        fun newInstance() = FavoritesFragment()
    }
}
