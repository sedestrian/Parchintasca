package mindtek.it.bikeapp.kotlin.extensions

import android.view.View

/**
 * Created by bipol on 8/1/2017.
 */
fun View.setVisible(){
    this.visibility = View.VISIBLE
}

fun View.setInvisible(){
    this.visibility = View.INVISIBLE
}

fun View.setGone(){
    this.visibility = View.GONE
}