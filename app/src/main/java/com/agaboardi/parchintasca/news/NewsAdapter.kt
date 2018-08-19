package com.agaboardi.parchintasca.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.news.domain.model.News

class NewsAdapter(val click: ((news: News) -> Unit)?): RecyclerView.Adapter<NewsViewHolder>() {
    private var news: MutableList<News> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position], position, click)
    }

    fun replaceNews(news: List<News>){
        this.news = news.toMutableList()
        notifyDataSetChanged()
    }
}