package com.agaboardi.parchintasca.common.extensions

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by bipol on 2/21/2018.
 */
fun AppCompatActivity.replaceFragment(fragment: Fragment, @IdRes container: Int) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(container, fragment)
    transaction.commit()
}