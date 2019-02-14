package com.agaboardi.parchintasca.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.agaboardi.parchintasca.favorites.FavoritesFragment
import com.agaboardi.parchintasca.list.FragmentList
import com.agaboardi.parchintasca.map.FragmentMap
import com.agaboardi.parchintasca.news.FragmentNews

class NavigationPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val mapFragment by lazy { FragmentMap.newInstance() }
    private val listFragment by lazy { FragmentList.newInstance() }
    private val favoritesFragment by lazy { FavoritesFragment.newInstance() }
    private val newsFragment by lazy { FragmentNews.newInstance() }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> mapFragment
            1 -> listFragment
            2 -> favoritesFragment
            3 -> newsFragment
            else -> mapFragment
        }
    }

    override fun getCount(): Int = 4
}