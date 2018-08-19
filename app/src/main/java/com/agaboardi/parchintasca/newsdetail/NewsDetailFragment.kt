package com.agaboardi.parchintasca.newsdetail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.agaboardi.parchintasca.R

class NewsDetailFragment : Fragment(), NewsDetailContract.View {
    private var presenter: NewsDetailContract.Presenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setPresenter(presenter: NewsDetailContract.Presenter) {
        this.presenter = presenter
    }
}
