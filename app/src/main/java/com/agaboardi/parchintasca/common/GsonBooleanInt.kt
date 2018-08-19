package com.agaboardi.parchintasca.common

import com.google.gson.*
import java.lang.reflect.Type


/**
 * Created by bipol on 3/6/2018.
 */
internal class GsonBooleanInt : JsonDeserializer<Boolean> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type,
                             context: JsonDeserializationContext): Boolean? {
        val code = json.asInt
        return when (code) {
            0 -> false
            1 -> true
            else -> null
        }
    }

    companion object {
        fun getGson(): Gson {
            val builder = GsonBuilder()
            builder.registerTypeAdapter(Boolean::class.java, GsonBooleanInt())
            return builder.create()
        }
    }
}