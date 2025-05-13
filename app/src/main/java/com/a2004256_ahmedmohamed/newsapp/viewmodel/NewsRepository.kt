package com.a2004256_ahmedmohamed.newsapp.viewmodel

import com.a2004256_ahmedmohamed.newsapp.interfaces.RetrofitInstance
import com.a2004256_ahmedmohamed.newsapp.interfaces.RoomDao
import com.a2004256_ahmedmohamed.newsapp.model.NewsApiResponse
import com.a2004256_ahmedmohamed.newsapp.model.NewsModel
import com.a2004256_ahmedmohamed.newsapp.model.RoomModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsRepository(private val roomDao: RoomDao) {

    suspend fun getNews(apiKey: String): List<NewsModel> {
        val response: Response<NewsApiResponse> = RetrofitInstance.api.getNews(apiKey)
        return if (response.isSuccessful) {
            response.body()?.articles ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getFavoriteNews(url: String): RoomModel? {
        return roomDao.getFavoriteNews(url)
    }

    suspend fun insertNews(news: RoomModel) {
        roomDao.insertFavoriteNews(news)
    }

    suspend fun deleteNews(news: RoomModel) {
        roomDao.deleteFavoriteNews(news)
    }

    suspend fun getAllFavoriteNews(): List<RoomModel> {
        return roomDao.getAllFavoriteNews()
    }

}