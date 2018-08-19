package com.agaboardi.parchintasca.data.source.local.db.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.agaboardi.parchintasca.data.source.local.db.models.ModelFavorite
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import io.reactivex.Flowable

/**
 * Created by bipol on 3/8/2018.
 */
@Dao
interface DaoPark {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveParks(parks: MutableList<Park>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePark(park: Park)

    @Query("SELECT * FROM park")
    fun getParksFlowable(): Flowable<List<Park>>

    @Query("SELECT * FROM park INNER JOIN favorites ON park.id = favorites.id WHERE favorite = 1")
    fun getFavoritesFlowable(): Flowable<List<Park>>

    @Query("SELECT * FROM park WHERE id = :park_id LIMIT 1")
    fun getParkById(park_id: Int): Flowable<Park>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setFavorite(favorite: ModelFavorite)

    @Query("DELETE FROM favorites WHERE id = :id")
    fun removeFavorite(id: Int)

    @Query("SELECT favorite FROM favorites WHERE id = :id")
    fun isFavorite(id: Int): Boolean

    @Query("DELETE FROM park")
    fun clear()

    @Query("SELECT COUNT(*) FROM park")
    fun count(): Int
}