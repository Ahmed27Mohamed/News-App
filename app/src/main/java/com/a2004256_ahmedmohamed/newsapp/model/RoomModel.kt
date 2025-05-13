package com.a2004256_ahmedmohamed.newsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_news")
data class RoomModel (
    val title: String,
    val description: String,
    val urlToImage: String,
    @PrimaryKey
    val url: String
)