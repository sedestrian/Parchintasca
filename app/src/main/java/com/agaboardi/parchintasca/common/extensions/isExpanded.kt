package com.agaboardi.parchintasca.common.extensions

import android.support.design.widget.BottomSheetBehavior
import android.view.View

fun <T: View> BottomSheetBehavior<T>.isExpanded(): Boolean{
    return this.state == BottomSheetBehavior.STATE_EXPANDED
}

fun <T: View>BottomSheetBehavior<T>.isCollapsed(): Boolean{
    return this.state == BottomSheetBehavior.STATE_COLLAPSED
}

fun <T: View>BottomSheetBehavior<T>.isHidden(): Boolean{
    return this.state == BottomSheetBehavior.STATE_HIDDEN
}

fun <T: View>BottomSheetBehavior<T>.expand(){
    this.state = BottomSheetBehavior.STATE_EXPANDED
}

fun <T: View>BottomSheetBehavior<T>.collapse(){
    this.state = BottomSheetBehavior.STATE_COLLAPSED
}

fun <T: View>BottomSheetBehavior<T>.hide(){
    this.state = BottomSheetBehavior.STATE_HIDDEN
}