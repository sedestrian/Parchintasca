package com.agaboardi.parchintasca.newsdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.agaboardi.parchintasca.R

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
    }

    companion object {
        const val NEWS_ID = "NEWS_ID"
    }
}
