package com.berdimyradov.news.data

import com.berdimyradov.news.data.source.local.entity.ArticleEntity
import com.berdimyradov.news.data.source.local.entity.CategoryEntity
import com.berdimyradov.news.data.source.local.entity.CategoryWithArticle
import com.berdimyradov.news.data.source.remote.dto.ArticleDto
import com.berdimyradov.news.data.source.remote.dto.SourceDto
import com.berdimyradov.news.data.source.remote.response.SourcesResponse
import com.berdimyradov.news.domain.model.Article
import com.berdimyradov.news.domain.model.Category

fun ArticleDto.toEntity(category: String): ArticleEntity {
    return ArticleEntity(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage,
        category = category
    )
}

fun CategoryWithArticle.toCategory(): Category {
    return Category(
        id = this.categoryEntity.id,
        category = this.categoryEntity.category,
        articles = this.articles.map { it.toArticle() }
    )
}

fun SourcesResponse.toCategoriesEntity(): List<CategoryEntity> {
    return this.sources
        ?.mapNotNull { source ->
            source.category
        }
        ?.toSet()
        ?.map { category ->
            CategoryEntity(
                id = category,
                category = category
            )
        } ?: emptyList()
}

fun ArticleEntity.toArticle(): Article {
    return Article(
        author = this.author ?: "",
        content = this.content ?: "",
        description = this.description ?: "",
        publishedAt = this.publishedAt ?: "",
        title = this.title ?: "",
        url = this.url ?: "",
        imageUrl = this.urlToImage ?: ""
    )
}