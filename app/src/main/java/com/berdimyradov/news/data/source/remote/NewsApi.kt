package com.berdimyradov.news.data.source.remote

import com.berdimyradov.news.data.source.remote.response.ArticlesResponse
import com.berdimyradov.news.data.source.remote.response.SourcesResponse
import com.berdimyradov.news.utils.Config
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale

interface NewsApi {

    companion object {
        const val BASE_URL = "https://newsapi.org"
        private const val TOP_HEADLINES = "/v2/top-headlines"
        private const val TOP_HEADLINES_SOURCES = "$TOP_HEADLINES/sources"
    }

    @GET(TOP_HEADLINES_SOURCES)
    suspend fun getSources(
        @Query("apiKey") apiKey: String = Config.NEWS_API_KEY
    ): SourcesResponse

    @GET(TOP_HEADLINES)
    suspend fun getArticles(
        @Query("apiKey") apiKey: String = Config.NEWS_API_KEY,
        @Query("country") country: String = Locale.getDefault().language,
        @Query("category") category: String
    ): ArticlesResponse
}