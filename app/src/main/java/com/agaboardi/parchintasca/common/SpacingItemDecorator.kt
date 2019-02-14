package com.agaboardi.parchintasca.common

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpacingItemDecorator(val start: Int = 0, val top: Int = 0, val end: Int = 0, val bottom: Int = 0): RecyclerView.ItemDecoration() {

    constructor(vertical: Int = 0, horizontal: Int = 0) : this(horizontal / 2, vertical / 2, horizontal / 2, vertical / 2)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = start
        outRect.right = end
        outRect.top = top
        outRect.bottom = bottom
    }
}