package com.agaboardi.parchintasca.data.source.local.db.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

@Dao
interface DaoFavorites {
    @Query("SELECT id FROM favorites WHERE favorite = 1")
    fun getFavoriteIds(): MutableList<Int>
}