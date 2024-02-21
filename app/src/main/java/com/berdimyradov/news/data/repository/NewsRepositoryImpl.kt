package com.berdimyradov.news.data.repository

import com.berdimyradov.news.data.source.local.dao.NewsDao
import com.berdimyradov.news.data.source.local.entity.CategoryEntity
import com.berdimyradov.news.data.source.remote.NewsApi
import com.berdimyradov.news.data.toArticle
import com.berdimyradov.news.data.toCategoriesEntity
import com.berdimyradov.news.data.toCategory
import com.berdimyradov.news.data.toEntity
import com.berdimyradov.news.domain.model.Category
import com.berdimyradov.news.domain.repository.NewsRepository
import com.berdimyradov.news.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dao: NewsDao,
    private val api: NewsApi
) : NewsRepository {
    override suspend fun getNews(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        try {
            val categories = dao.getAllArticles().map { it.toCategory() }
            emit(Resource.Loading(categories))

            val categoriesFromApi =
                api.getSources().toCategoriesEntity()
            dao.addCategories(categoriesFromApi)

            val newCategories = fetchArticles(categoriesFromApi)
            emit(Resource.Success(newCategories))
        } catch (e: NullPointerException) {
            emit(Resource.Error("Oops, something went wrong"))
        } catch (e: HttpException) {
            emit(Resource.Error("Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection"))
        }
    }

    private suspend fun fetchArticles(categories: List<CategoryEntity>): List<Category> =
        withContext(Dispatchers.IO) {
            dao.deleteAllArticles()
            categories.map { category ->
                async {
                    val articles = api.getArticles(category = category.category)
                        .articles?.map { it.toEntity(category.category) }
                        ?: emptyList()
                    dao.addArticles(articles)
                    Category(
                        id = category.id,
                        category = category.category,
                        articles = articles.map { it.toArticle() }
                    )
                }
            }.awaitAll()
        }
}