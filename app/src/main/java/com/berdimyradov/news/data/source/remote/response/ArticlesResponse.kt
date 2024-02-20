package com.berdimyradov.news.data.source.remote.response

import com.berdimyradov.news.data.source.remote.dto.ArticleDto
import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("totalResults") val totalResults: Int? = null,
    @SerializedName("articles") val articles: List<ArticleDto>? = null
)