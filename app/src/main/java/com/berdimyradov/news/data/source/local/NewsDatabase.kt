package com.berdimyradov.news.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.berdimyradov.news.data.source.local.dao.NewsDao
import com.berdimyradov.news.data.source.local.entity.ArticleEntity
import com.berdimyradov.news.data.source.local.entity.CategoryEntity

@Database(
    entities = [CategoryEntity::class, ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun dao(): NewsDao
}