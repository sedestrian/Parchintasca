package com.agaboardi.parchintasca.newsdetail

import com.agaboardi.parchintasca.common.base.UseCaseHandler
import com.agaboardi.parchintasca.newsdetail.domain.usecase.GetReport

class NewsDetailPresenter(
        val view: NewsDetailContract.View,
        val useCaseHandler: UseCaseHandler,
        val getReport: GetReport
): NewsDetailContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun start() {

    }
}