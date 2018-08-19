package com.agaboardi.parchintasca.map.domain.model

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import org.jetbrains.anko.dip

/**
 * Created by bipol on 3/21/2018.
 */
class ParkClusterItem(
        private var position: LatLng,
        @DrawableRes var icon: Int,
        var parkId: Int
): ClusterItem {
    override fun getSnippet(): String = ""

    override fun getTitle(): String = ""

    override fun getPosition(): LatLng = position
}