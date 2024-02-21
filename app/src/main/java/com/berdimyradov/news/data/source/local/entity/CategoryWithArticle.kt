package com.berdimyradov.news.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithArticle(
    @Embedded val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "category",
        entityColumn = "category"
    ) val articles: List<ArticleEntity>
)
