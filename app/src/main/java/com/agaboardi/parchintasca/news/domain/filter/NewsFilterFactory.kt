package com.agaboardi.parchintasca.news.domain.filter

class NewsFilterFactory {
    private val mFilters = HashMap<NewsFilterType, NewsFilter>()

    init {
        mFilters[NewsFilterType.FILTER_ALL] = NewsFilterAll()
    }

    fun create(filterType: NewsFilterType?): NewsFilter? {
        return mFilters[filterType]
    }
}