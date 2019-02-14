package com.agaboardi.parchintasca.data.source.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.agaboardi.parchintasca.data.source.local.db.converters.AddressTypeConverter
import com.agaboardi.parchintasca.data.source.local.db.daos.DaoFavorites
import com.agaboardi.parchintasca.data.source.local.db.daos.DaoNews
import com.agaboardi.parchintasca.data.source.local.db.daos.DaoPark
import com.agaboardi.parchintasca.data.source.local.db.models.ModelFavorite
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.news.domain.model.News

/**
 * Created by bipol on 3/8/2018.
 */
@Database(version = 7, entities = [
    Park::class,
    ModelFavorite::class,
    News::class
])
@TypeConverters(AddressTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun parkDao(): DaoPark
    abstract fun favoritesDao(): DaoFavorites
    abstract fun newsDao(): DaoNews

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun setInstance(instance: AppDatabase) {
            INSTANCE = instance
        }

        fun getInstance(): AppDatabase {
            INSTANCE?.let {
                return it
            } ?: throw Exception("No room instance has been set!!")
        }
    }
}