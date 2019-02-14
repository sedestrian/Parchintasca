package com.agaboardi.parchintasca.news.domain.ui

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

class NewsDivider(val dividerDrawable: Drawable, val sidePadding: Int, val verticalSpacing: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = verticalSpacing / 2
        outRect.bottom = verticalSpacing / 2
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val left = parent.paddingLeft + sidePadding
        val right = parent.width - (parent.paddingRight + sidePadding)
        val childCount = parent.childCount

        for (i in 0 until childCount - 1){
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin + verticalSpacing / 2
            val bottom = top + dividerDrawable.intrinsicHeight

            dividerDrawable.setBounds(left, top, right, bottom)

            dividerDrawable.draw(c)
        }
    }
}