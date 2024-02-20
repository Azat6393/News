package com.berdimyradov.news.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berdimyradov.news.data.source.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories(categories: List<CategoryEntity>)

    @Query("SELECT * FROM category")
    fun getAllArticles(): Flow<List<CategoryEntity>>
}