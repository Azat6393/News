package com.berdimyradov.news.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("article")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val category: String,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)