package com.agaboardi.parchintasca.common.views

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NoGesturePager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    var isPagingEnabled: Boolean = false

    init {
        this.isPagingEnabled = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.isPagingEnabled) {
            super.onTouchEvent(event)
        } else false

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.isPagingEnabled) {
            super.onInterceptTouchEvent(event)
        } else false

    }
}