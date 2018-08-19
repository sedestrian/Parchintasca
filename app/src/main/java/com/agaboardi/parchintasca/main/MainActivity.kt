package com.agaboardi.parchintasca.main

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager
import android.view.Menu
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.map.domain.model.ParkCategory
import com.claudiodegio.msv.OnFilterViewListener
import com.claudiodegio.msv.OnSearchViewListener
import com.claudiodegio.msv.model.Filter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainContract.View, SearchContract.Host {
    private var filterInterfaces: MutableList<FilterInterface> = mutableListOf()
    private var searchInterfaces: MutableList<SearchInterface> = mutableListOf()
    private var presenter: MainContract.Presenter? = null
    private var hasFilters: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        MainPresenter(
                this
        )

        setupUiInteraction()

        presenter?.start()
    }

    override fun addSubscriber(subscriber: SearchContract.Subscriber) {
        filterInterfaces.add(subscriber)
        searchInterfaces.add(subscriber)
    }

    private fun setupUiInteraction(){
        bottomAppBar.setOnNavigationItemSelectedListener {
            presenter?.onNavigationItemSelected(it)
            true
        }

        searchView.setOnFilterViewListener(object : OnFilterViewListener {
            override fun onFilterAdded(filter: Filter) {
                filterInterfaces.forEach {
                    it.onFilterAdded(filter)
                }
            }

            override fun onFilterChanged(filters: MutableList<Filter>) {
                filterInterfaces.forEach {
                    it.onFilterChanged(filters)
                }
            }

            override fun onFilterRemoved(filter: Filter) {
                filterInterfaces.forEach {
                    it.onFilterRemoved(filter)
                }
            }
        })

        searchView.setOnSearchViewListener(object : OnSearchViewListener {
            override fun onSearchViewClosed() {
                searchInterfaces.forEach {
                    it.onSearchViewClosed()
                }
            }

            override fun onSearchViewShown() {
                searchInterfaces.forEach {
                    it.onSearchViewShown()
                }
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchInterfaces.forEach {
                    it.onQueryTextSubmit(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) {
                searchInterfaces.forEach {
                    it.onQueryTextChange(newText)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return true
    }

    override fun setupFilters() {
        if (!hasFilters) {
            val filters = ParkCategory.values()
            filters.forEachIndexed { index, type ->
                val filter = Filter(index, getString(type.named), type.value, type.drawable, ContextCompat.getColor(this, android.R.color.transparent))
                searchView.addFilter(filter)
            }
            hasFilters = true
        }
    }

    override fun setupViewPager() {
        pager.adapter = NavigationPagerAdapter(supportFragmentManager)
        pager.offscreenPageLimit = 4
        pager.isPagingEnabled = false
        pager.currentItem = 0
        bottomAppBar.selectedItemId = R.id.map
    }

    override fun showMap() {
        toolbarTitle.text = getString(R.string.title_map)
        pager.currentItem = 0
        blurToolbar()
    }

    override fun showList() {
        toolbarTitle.text = getString(R.string.title_list)
        pager.currentItem = 1
        consolidateToolbar()
    }

    override fun showFavorites() {
        toolbarTitle.text = getString(R.string.title_favorite)
        pager.currentItem = 2
        consolidateToolbar()
    }

    override fun showNews() {
        toolbarTitle.text = getString(R.string.title_news)
        pager.currentItem = 3
        searchView.closeSearch()
        consolidateToolbar()
    }

    private fun blurToolbar() {
        appBar.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun consolidateToolbar() {
        appBar.setBackgroundColor(Color.WHITE)
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    override fun isActive(): Boolean = !isFinishing && !isDestroyed
}