package mindtek.it.bikeapp.kotlin.extensions

import java.util.*

/**
 * Created by bipol on 8/2/2017.
 */
fun Date.toCalendar(): Calendar{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this.time
    return calendar
}