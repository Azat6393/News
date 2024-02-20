package com.berdimyradov.news.di

import android.app.Application
import androidx.room.Room
import com.berdimyradov.news.data.source.local.Converters
import com.berdimyradov.news.data.source.local.NewsDatabase
import com.berdimyradov.news.data.source.remote.NewsApi
import com.berdimyradov.news.utils.GsonParser
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext app: Application): NewsDatabase {
        return Room.databaseBuilder(
            app, NewsDatabase::class.java, "articles_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}