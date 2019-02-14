package com.agaboardi.parchintasca.news

import com.agaboardi.parchintasca.common.base.BasePresenter
import com.agaboardi.parchintasca.common.base.BaseView
import com.agaboardi.parchintasca.news.domain.model.News

interface NewsContract {
    interface View: BaseView<Presenter>{
        fun showNewsError()
        fun setLoadingNews(visible: Boolean)
        fun isValid(): Boolean
        fun showNews(news: List<News>)
        fun goToNewsDetail(news: News)

    }

    interface Presenter: BasePresenter{

        fun loadNews(forceReload: Boolean, showLoadingUi: Boolean)
        fun loadNews(forceReload: Boolean)
        fun openNews(news: News)
    }
}