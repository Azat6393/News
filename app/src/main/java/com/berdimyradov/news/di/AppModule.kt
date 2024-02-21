package com.berdimyradov.news.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.berdimyradov.news.data.repository.NewsRepositoryImpl
import com.berdimyradov.news.data.source.local.NewsDatabase
import com.berdimyradov.news.data.source.local.dao.NewsDao
import com.berdimyradov.news.data.source.remote.NewsApi
import com.berdimyradov.news.domain.repository.NewsRepository
import com.berdimyradov.news.domain.use_case.GetNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetNewsUseCase(
        repository: NewsRepository
    ) = GetNewsUseCase(repository)

    @Provides
    @Singleton
    fun provideNewsRepository(
        dao: NewsDao,
        api: NewsApi
    ): NewsRepository {
        return NewsRepositoryImpl(dao, api)
    }

    @Provides
    @Singleton
    fun provideDao(database: NewsDatabase) = database.dao()

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext app: Context): NewsDatabase {
        return Room.databaseBuilder(
            app, NewsDatabase::class.java, "news_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): NewsApi {
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        try {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(logging)
            builder.connectTimeout(30, TimeUnit.SECONDS)
            builder.writeTimeout(30, TimeUnit.SECONDS)
            builder.readTimeout(30, TimeUnit.SECONDS)
            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}