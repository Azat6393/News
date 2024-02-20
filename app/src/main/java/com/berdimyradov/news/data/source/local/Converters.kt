package com.berdimyradov.news.data.source.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.berdimyradov.news.data.source.local.entity.CategoryEntity
import com.berdimyradov.news.utils.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromCategoriesJson(json: String): List<CategoryEntity>{
        return jsonParser.fromJson<ArrayList<CategoryEntity>>(
            json,
            object : TypeToken<ArrayList<CategoryEntity>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toCategoriesJson(categories: List<CategoryEntity>): String{
        return jsonParser.toJson(
            categories,
            object : TypeToken<ArrayList<CategoryEntity>>(){}.type
        ) ?: "[]"
    }
}