package com.berdimyradov.news.domain.repository

import com.berdimyradov.news.domain.model.Category
import com.berdimyradov.news.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNews(): Flow<Resource<List<Category>>>
}