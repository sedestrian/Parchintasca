package mindtek.it.bikeapp.kotlin.extensions

import android.content.Context
import android.widget.Toast

/**
 * Created by bipol on 8/23/2017.
 */
fun Context.toast(text: String, long: Boolean){
    Toast.makeText(this, text, if(long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}