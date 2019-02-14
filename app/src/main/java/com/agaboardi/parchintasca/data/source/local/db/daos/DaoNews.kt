package com.agaboardi.parchintasca.data.source.local.db.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.agaboardi.parchintasca.news.domain.model.News
import io.reactivex.Flowable

@Dao
interface DaoNews {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNews(news: MutableList<News>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNews(news: News)

    @Query("SELECT * FROM news")
    fun getNewsFlowable(): Flowable<List<News>>

    @Query("SELECT * FROM news WHERE id = :news_id LIMIT 1")
    fun getNewsById(news_id: Int): Flowable<News>

    @Query("DELETE FROM news")
    fun clear()

    @Query("SELECT COUNT(*) FROM news")
    fun count(): Int
}