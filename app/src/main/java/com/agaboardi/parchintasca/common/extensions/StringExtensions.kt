package com.agaboardi.parchintasca.common.extensions

import com.agaboardi.parchintasca.R
import mindtek.it.bikeapp.kotlin.extensions.resetTime
import mindtek.it.bikeapp.kotlin.extensions.toCalendar
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by bipol on 3/21/2018.
 */
fun String.getResourceId(c: Class<*>): Int {
    try {
        val idField = c.getDeclaredField(this)
        return idField.getInt(idField)
    } catch (e: Exception) {
        throw RuntimeException("No resource ID found for: "
                + this + " / " + c, e)
    }

}

fun String.getDrawableId(): Int {
    try {
        val idField = R.drawable::class.java.getDeclaredField(this)
        return idField.getInt(idField)
    } catch (e: Exception) {
        throw RuntimeException("No resource ID found for: "
                + this + " / " + R.drawable::class.java, e)
    }

}

fun String.toCalendar(pattern: String = "dd MMM"): Calendar{
    val cal = Calendar.getInstance()
    val sdf = SimpleDateFormat(pattern, Locale.ENGLISH)
    val temp = sdf.parse(this).toCalendar()
    cal[Calendar.DAY_OF_MONTH] = temp[Calendar.DAY_OF_MONTH]
    cal[Calendar.MONTH] = temp[Calendar.MONTH]
    cal.resetTime()
    return cal
}