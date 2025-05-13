package com.a2004256_ahmedmohamed.newsapp.interfaces

import com.a2004256_ahmedmohamed.newsapp.model.NewsApiResponse
import com.a2004256_ahmedmohamed.newsapp.model.NewsModel
import com.a2004256_ahmedmohamed.newsapp.model.RoomModel
import com.a2004256_ahmedmohamed.newsapp.viewmodel.NewsRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitDao {
    @GET("v2/everything")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("domains") sortBy: String = "wsj.com"
    ): Response<NewsApiResponse>
}