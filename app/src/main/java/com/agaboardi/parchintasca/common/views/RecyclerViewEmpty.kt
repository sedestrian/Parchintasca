package com.agaboardi.parchintasca.common.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import mindtek.it.bikeapp.kotlin.extensions.setGone
import mindtek.it.bikeapp.kotlin.extensions.setVisible

/**
 * Created by alessandro on 15/03/18.
 */
class RecyclerViewEmpty: RecyclerView{
    private var emptyView: View? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        super.setAdapter(adapter)

        adapter?.registerAdapterDataObserver(mEmptyObserver)

        mEmptyObserver.onChanged()
    }

    fun setEmptyView(emptyView: View) {
        this.emptyView = emptyView
        mEmptyObserver.onChanged()
    }

    private fun showEmpty() {
        emptyView?.setVisible()
        setGone()
    }

    private fun showRecycler() {
        emptyView?.setGone()
        setVisible()
    }


    private val mEmptyObserver = object : RecyclerView.AdapterDataObserver() {

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            checkEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            checkEmpty()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            checkEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            checkEmpty()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            checkEmpty()
        }

        override fun onChanged() {
            super.onChanged()
            checkEmpty()
        }

        private fun checkEmpty() {
            val adapter = adapter
            if (emptyView != null) {
                if (adapter != null) {
                    if (adapter.itemCount <= 0) {
                        showEmpty()
                    } else {
                        showRecycler()
                    }
                } else {
                    showEmpty()
                }
            }
        }
    }
}