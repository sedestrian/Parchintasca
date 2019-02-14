package com.agaboardi.parchintasca.main

import android.view.MenuItem
import com.agaboardi.parchintasca.common.base.BasePresenter
import com.agaboardi.parchintasca.common.base.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {
        fun isActive(): Boolean
        fun setupViewPager()
        fun setupFilters()
        fun showMap()
        fun showList()
        fun showNews()
        fun showFavorites()
    }

    interface Presenter : BasePresenter {
        fun onNavigationItemSelected(item: MenuItem)
    }
}