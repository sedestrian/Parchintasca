package com.agaboardi.parchintasca.news.domain.filter

import com.agaboardi.parchintasca.news.domain.model.News

interface NewsFilter {
    fun filter(news: List<News>, query: String?): List<News>
}