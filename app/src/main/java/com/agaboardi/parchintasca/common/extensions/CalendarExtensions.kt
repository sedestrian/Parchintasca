package mindtek.it.bikeapp.kotlin.extensions

import java.util.*

/**
 * Created by bipol on 8/2/2017.
 */

fun Calendar.sameDayAs(calendar: Calendar?): Boolean {
    calendar?.let {
        val sameDay = this[Calendar.DAY_OF_MONTH] == calendar[Calendar.DAY_OF_MONTH]
        val sameMonth = this[Calendar.MONTH] == calendar[Calendar.MONTH]
        val sameYear = this[Calendar.YEAR] == calendar[Calendar.YEAR]
        return sameDay && sameMonth && sameYear
    } ?: return false
}

fun Calendar.resetTime(){
    set(Calendar.HOUR, 0)
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}