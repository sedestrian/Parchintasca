package com.agaboardi.parchintasca.common.injection

import com.agaboardi.parchintasca.common.base.AppExecutors
import com.agaboardi.parchintasca.common.base.UseCaseHandler
import com.agaboardi.parchintasca.data.source.MainRepository
import com.agaboardi.parchintasca.data.source.NewsRepository
import com.agaboardi.parchintasca.data.source.local.news.NewsLocalDataSource
import com.agaboardi.parchintasca.data.source.local.parks.MainLocalDataSource
import com.agaboardi.parchintasca.data.source.remote.main.MainRemoteDataSource
import com.agaboardi.parchintasca.data.source.remote.news.NewsRemoteDataSource
import com.agaboardi.parchintasca.favorites.domain.usecase.GetFavorites
import com.agaboardi.parchintasca.map.domain.filter.ParkFilterFactory
import com.agaboardi.parchintasca.map.domain.usecase.GetPark
import com.agaboardi.parchintasca.map.domain.usecase.GetParks
import com.agaboardi.parchintasca.news.domain.filter.NewsFilterFactory
import com.agaboardi.parchintasca.news.domain.usecase.GetNews
import mindtek.it.bikeapp.kotlin.extensions.db

object Injection {
    fun provideGetParks(): GetParks {
        return GetParks(provideMainRepository(), ParkFilterFactory())
    }

    fun provideGetNews(): GetNews {
        return GetNews(provideNewsRepository(), NewsFilterFactory())
    }

    fun provideGetFavorites(): GetFavorites {
        return GetFavorites(provideMainRepository(), ParkFilterFactory())
    }

    fun provideGetPark(): GetPark {
        return GetPark(provideMainRepository())
    }

    fun provideMainRepository(): MainRepository {
        return MainRepository(
                MainLocalDataSource.getInstance(AppExecutors(), db.parkDao()),
                MainRemoteDataSource.getInstance()
        )
    }

    fun provideNewsRepository(): NewsRepository {
        return NewsRepository(
                NewsLocalDataSource.getInstance(AppExecutors(), db.newsDao()),
                NewsRemoteDataSource.getInstance()
        )
    }

    fun provideUseCaseHandler(): UseCaseHandler {
        return UseCaseHandler.getInstance()
    }
}