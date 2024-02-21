package com.berdimyradov.news.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.berdimyradov.news.data.source.local.entity.ArticleEntity
import com.berdimyradov.news.data.source.local.entity.CategoryEntity
import com.berdimyradov.news.data.source.local.entity.CategoryWithArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories(categories: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM article")
    suspend fun deleteAllArticles()

    @Transaction
    @Query("SELECT * FROM category")
    suspend fun getAllArticles(): List<CategoryWithArticle>
}