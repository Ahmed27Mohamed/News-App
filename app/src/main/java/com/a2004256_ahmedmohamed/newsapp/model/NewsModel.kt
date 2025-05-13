package com.a2004256_ahmedmohamed.newsapp.model

data class NewsModel (
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String
)

data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsModel>
)