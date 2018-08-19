package com.agaboardi.parchintasca.data.source.local.db.converters

import android.arch.persistence.room.TypeConverter
import android.location.Address
import com.google.gson.Gson

/**
 * Created by alessandro on 15/03/18.
 */
class AddressTypeConverter{
    @TypeConverter
    fun toJson(address: Address): String{
        return Gson().toJson(address)
    }

    @TypeConverter
    fun toAddress(json: String): Address{
        return Gson().fromJson<Address>(json, Address::class.java)
    }
}