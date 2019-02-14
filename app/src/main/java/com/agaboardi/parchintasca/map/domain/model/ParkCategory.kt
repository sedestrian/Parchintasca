package com.agaboardi.parchintasca.map.domain.model

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.agaboardi.parchintasca.R

/**
 * Created by bipol on 3/8/2018.
 */
enum class ParkCategory(val value: Int, @DrawableRes val drawable: Int, @StringRes val named: Int) {
    BABIES(1, R.drawable.babies, R.string.babies),
    EVENTS(2, R.drawable.events, R.string.events),
    NATURE(3, R.drawable.nature, R.string.nature),
    PANORAMA(4, R.drawable.panorama, R.string.panorama),
    RELAX(5, R.drawable.relax, R.string.relax),
    SPORT(6, R.drawable.sport, R.string.sport);

    companion object {
        fun drawableFor(id: Int) = values().first { it.value == id }.drawable
        fun named(id: Int) = values().first { it.value == id }.named
        fun from(id: Int) = values().first { it.value == id }
    }
}