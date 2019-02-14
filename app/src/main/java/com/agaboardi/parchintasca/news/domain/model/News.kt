package com.agaboardi.parchintasca.news.domain.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "news")
class News(
        @PrimaryKey
        var id: Int,
        var title: String,
        var subtitle: String,
        var date: String,
        var picture_url: String,
        var content: String,
        var link: String
)