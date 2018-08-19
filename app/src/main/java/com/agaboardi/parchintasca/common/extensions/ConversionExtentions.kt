package com.agaboardi.parchintasca.common.extensions

import android.content.res.Resources

/**
 * Created by Alessandro Gaboardi on 4/8/2017.
 */

val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()