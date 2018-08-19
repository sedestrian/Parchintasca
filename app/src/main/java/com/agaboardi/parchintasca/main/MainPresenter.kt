package com.agaboardi.parchintasca.main

import android.view.MenuItem
import com.agaboardi.parchintasca.R

class MainPresenter(
        val view: MainContract.View
): MainContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun start() {
        view.setupViewPager()
        view.setupFilters()
    }

    override fun onNavigationItemSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.map -> view.showMap()
            R.id.list -> view.showList()
            R.id.favorites -> view.showFavorites()
            R.id.news -> view.showNews()
            else -> view.showMap()
        }
    }
}