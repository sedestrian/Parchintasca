package com.agaboardi.parchintasca.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.map.domain.model.ParkClusterItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import org.jetbrains.anko.dip

/**
 * Created by bipol on 3/21/2018.
 */
class ParksClusterRenderer(val context: Context, val map: GoogleMap, val clusterManager: ClusterManager<ParkClusterItem>) :
        DefaultClusterRenderer<ParkClusterItem>(context, map, clusterManager) {

    override fun getColor(clusterSize: Int): Int = ContextCompat.getColor(context, R.color.primaryColor)

    override fun onBeforeClusterItemRendered(item: ParkClusterItem, markerOptions: MarkerOptions) {
        markerOptions.icon(vectorResourceId(context, item.icon))
                .position(item.position)
    }

    override fun onClusterItemRendered(clusterItem: ParkClusterItem, marker: Marker) {
        marker.tag = clusterItem.parkId
    }

    private fun resizeIcon(@DrawableRes res: Int): Bitmap {
        val height = context.dip(40)
        val width = context.dip(26)
        val bitmapdraw = ContextCompat.getDrawable(context, res) as BitmapDrawable
        val b = bitmapdraw.bitmap
        return Bitmap.createScaledBitmap(b, width, height, false)
    }

    fun vectorResourceId(context: Context, @DrawableRes vectorRes: Int): BitmapDescriptor?{
        val vectorDrawable = ContextCompat.getDrawable(context, vectorRes)
        vectorDrawable?.let {
            vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
            val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            vectorDrawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        } ?: return null
    }
}