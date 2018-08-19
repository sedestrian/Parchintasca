package com.agaboardi.parchintasca.parkdetail


import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.common.GlideApp
import com.agaboardi.parchintasca.common.extensions.getDrawableId
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.list.AdapterTags
import com.agaboardi.parchintasca.parkdetail.domain.model.ParkCharacteristics
import com.agaboardi.parchintasca.parkdetail.domain.model.ParkDetails
import com.agaboardi.parchintasca.parkdetail.domain.model.ParkSeason
import kotlinx.android.synthetic.main.fragment_park_detail.*
import mindtek.it.bikeapp.kotlin.extensions.setGone
import org.jetbrains.anko.textColor

class ParkDetailFragment : Fragment(), ParkDetailContract.View {
    private var presenter: ParkDetailContract.Presenter? = null
    private var adapter: AdapterTags? = null
    private var detailAdapter: DetailAdapter? = null
    private var characteristicsAdapter: CharacteristicsAdapter? = null
    private var hidden: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_park_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.start()
    }

    override fun setupRecycler(){
        detailAdapter = DetailAdapter()
        detailRecycler.layoutManager = LinearLayoutManager(activity)
        detailRecycler.adapter = detailAdapter

        characteristicsAdapter = CharacteristicsAdapter()
        characteristicsRecycler.layoutManager = GridLayoutManager(activity, 5)
        characteristicsRecycler.adapter = characteristicsAdapter
    }

    override fun setupTags() {
        adapter = AdapterTags()
        tags.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        tags.adapter = adapter
    }

    override fun showTags(park: Park) {
        adapter?.setPark(park)
    }

    override fun setPresenter(presenter: ParkDetailContract.Presenter) {
        this.presenter = presenter
    }

    override fun showPark(park: Park) {
        showTags(park)
        showPicture(park)
        showCharacteristics(park)
        showDetails(park)
        collapsing.title = park.name
        parkName.text = park.name
        address.text = park.address?.getAddressLine(0)
    }

    private fun showPicture(park: Park) {
        try {
            val picture = ("p" + park.id + "_min").getDrawableId()
            GlideApp.with(this)
                    .load(picture)
                    .into(app_bar_image)
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
    }

    override fun showHours(summer: ParkSeason, winter: ParkSeason, season: Int) {
        firstSeason.text = summer.season
        firstOpening.text = summer.opening
        firstClosing.text = summer.closing
        secondSeason.text = winter.season
        secondOpening.text = winter.opening
        secondClosing.text = winter.closing
        if(summer.season.isBlank() || summer.opening.isBlank() || summer.closing.isBlank()){
            firstSeason.setGone()
            firstOpening.setGone()
            firstClosing.setGone()
        }
        if(winter.season.isBlank() || winter.opening.isBlank() || winter.closing.isBlank()){
            secondSeason.setGone()
            secondOpening.setGone()
            secondClosing.setGone()
        }
        if(season == ParkDetailPresenter.SPRING || season == ParkDetailPresenter.SUMMER) {
            firstSeason.textColor = Color.BLACK
            firstOpening.textColor = Color.BLACK
            firstClosing.textColor = Color.BLACK
            semicolon.textColor = Color.BLACK
            bullet.textColor = Color.BLACK
        }else{
            secondSeason.textColor = Color.BLACK
            secondOpening.textColor = Color.BLACK
            secondClosing.textColor = Color.BLACK
            semicolon2.textColor = Color.BLACK
            bullet2.textColor = Color.BLACK
        }
    }

    private fun showCharacteristics(park: Park){
        val characteristics = ParkCharacteristics(park)
        characteristicsAdapter?.setCharacteristics(characteristics.values)
    }

    private fun showDetails(park: Park){
        val details = ParkDetails(park)
        detailAdapter?.setDetails(details.values)
    }

    override fun setLoadingIndicator(visible: Boolean) {

    }

    override fun showError() {

    }

    companion object {
        fun newInstance() = ParkDetailFragment()
    }
}
