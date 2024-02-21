package com.berdimyradov.news.domain.model

data class Category(
    val id: String,
    val category: String,
    val articles: List<Article>
)
