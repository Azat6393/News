package com.berdimyradov.news.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.berdimyradov.news.data.source.local.dao.NewsDao
import com.berdimyradov.news.data.source.local.entity.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract val dao: NewsDao
}