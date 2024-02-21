package com.berdimyradov.news.domain.model

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val imageUrl: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            title,
            description
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}