package com.agaboardi.parchintasca.news

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.agaboardi.parchintasca.common.GlideApp
import com.agaboardi.parchintasca.news.domain.model.News
import kotlinx.android.synthetic.main.item_news.view.*
import java.text.FieldPosition

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val picture: ImageView = itemView.image
    val title: TextView = itemView.title
    val description: TextView = itemView.description

    fun bind(news: News, position: Int, click: ((news: News) -> Unit)?){
        GlideApp.with(itemView.context)
                .load(news.picture_url)
                .into(picture)
        title.text = news.title
        description.text = news.content
        itemView.setOnClickListener { click?.invoke(news) }
    }
}