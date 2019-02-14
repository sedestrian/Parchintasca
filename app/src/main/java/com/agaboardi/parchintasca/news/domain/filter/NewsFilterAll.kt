package com.agaboardi.parchintasca.news.domain.filter

import com.agaboardi.parchintasca.news.domain.model.News

class NewsFilterAll: NewsFilter {
    override fun filter(news: List<News>, query: String?): List<News> {
        return news.filter { news -> match(news.title, query) }
    }

    fun match(string: String, query: String?): Boolean{
        query?.let {
            return string.toLowerCase().contains(query.toLowerCase())
        }
        return true
    }
}