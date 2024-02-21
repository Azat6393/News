package com.berdimyradov.news.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity("category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val category: String,
)
