package com.agaboardi.parchintasca.news

import com.agaboardi.parchintasca.common.base.UseCase
import com.agaboardi.parchintasca.common.base.UseCaseHandler
import com.agaboardi.parchintasca.news.domain.filter.NewsFilterType
import com.agaboardi.parchintasca.news.domain.model.News
import com.agaboardi.parchintasca.news.domain.usecase.GetNews
import java.text.SimpleDateFormat
import java.util.*

class NewsPresenter(
        val view: NewsContract.View,
        val usecaseHandler: UseCaseHandler,
        val getNews: GetNews
) : NewsContract.Presenter {
    var firstTime: Boolean = true
    var currentFilter: NewsFilterType = NewsFilterType.FILTER_ALL
    var currentQuery: String = ""
    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    init {
        view.setPresenter(this)
    }

    override fun start() {
        loadNews(false)
    }

    override fun loadNews(forceReload: Boolean) {
        loadNews(forceReload || firstTime, true)
        firstTime = false
    }

    override fun loadNews(forceReload: Boolean, showLoadingUi: Boolean) {
        if (showLoadingUi) {
            view.setLoadingNews(true)
        }

        val requestValues = GetNews.RequestValues(
                forceReload, currentFilter, currentQuery
        )

        usecaseHandler.execute(getNews, requestValues, object : UseCase.UseCaseCallback<GetNews.ResponseValue> {
            override fun onSuccess(response: GetNews.ResponseValue) {
                val news = response.news

                if (showLoadingUi)
                    view.setLoadingNews(false)

                if (!view.isValid())
                    return

                handleNews(news)
            }

            override fun onError() {
                if (showLoadingUi)
                    view.setLoadingNews(false)

                view.showNewsError()
            }

        })
    }

    private fun handleNews(news: List<News>){
        val orderedNews = news.sortedByDescending { parser.parse(it.date) }
        view.showNews(orderedNews)
    }

    override fun openNews(news: News) {
        view.goToNewsDetail(news)
    }
}