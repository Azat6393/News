package com.berdimyradov.news.domain.use_case

import com.berdimyradov.news.domain.model.Category
import com.berdimyradov.news.domain.repository.NewsRepository
import com.berdimyradov.news.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repo: NewsRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Category>>> {
        return repo.getNews()
    }
}