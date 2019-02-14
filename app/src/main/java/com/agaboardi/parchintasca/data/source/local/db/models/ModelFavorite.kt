package com.agaboardi.parchintasca.data.source.local.db.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favorites")
class ModelFavorite(
        @PrimaryKey
        var id: Int = -1,
        var favorite: Boolean = false
) {
}