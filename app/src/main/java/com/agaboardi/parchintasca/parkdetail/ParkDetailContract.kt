package com.agaboardi.parchintasca.parkdetail

import com.agaboardi.parchintasca.common.base.BasePresenter
import com.agaboardi.parchintasca.common.base.BaseView
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.parkdetail.domain.model.ParkSeason

interface ParkDetailContract {
    interface View: BaseView<Presenter>{
        fun setLoadingIndicator(visible: Boolean)
        fun showPark(park: Park)
        fun showError()
        fun setupTags()
        fun showTags(park: Park)
        fun showHours(summer: ParkSeason, winter: ParkSeason, season: Int)
        fun setupRecycler()
    }

    interface Presenter: BasePresenter{
        fun loadPark(forceUpdate: Boolean)
        fun loadPark(forceUpdate: Boolean, showLoadingUI: Boolean)
    }
}