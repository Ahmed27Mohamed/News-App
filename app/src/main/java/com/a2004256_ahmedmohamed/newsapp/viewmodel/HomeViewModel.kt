package com.a2004256_ahmedmohamed.newsapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a2004256_ahmedmohamed.newsapp.model.NewsModel
import com.a2004256_ahmedmohamed.newsapp.model.RoomModel
import kotlinx.coroutines.launch

class HomeViewModel(application: Application, private val repository: NewsRepository) : AndroidViewModel(application) {

    private val _favoriteNews = MutableLiveData<List<RoomModel>>()
    val favoriteNews: LiveData<List<RoomModel>> get() = _favoriteNews

    private val _newsList = MutableLiveData<List<NewsModel>>()
    val newsList: LiveData<List<NewsModel>> get() = _newsList

    private val _mutableLiveData = MutableLiveData<List<NewsModel>>()
    val mutableLiveData: MutableLiveData<List<NewsModel>> get() = _mutableLiveData

    private val apiKey = "648faee058f94873a65184cba81406dd"

    fun fetchNewsOnRoom(query: String) {
        viewModelScope.launch {
            val result = repository.getNews(apiKey)

            val filtered = if (query.isNotEmpty()) {
                result.filter { it?.title?.contains(query, ignoreCase = true) == true }
            } else {
                result
            }

            _mutableLiveData.postValue(filtered)
        }
    }

    fun toggleFavorite(news: RoomModel) {
        viewModelScope.launch {
            val existingNews = repository.getFavoriteNews(news.url)
            if (existingNews == null) {
                repository.insertNews(news)
                Toast.makeText(getApplication(), "Added to favorites", Toast.LENGTH_SHORT).show()
            } else {
                repository.deleteNews(news)
                Toast.makeText(getApplication(), "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
            loadFavoriteNews()
        }
    }

    fun loadFavoriteNews() {
        viewModelScope.launch {
            _favoriteNews.postValue(repository.getAllFavoriteNews())
        }
    }

    fun fetchNews() {
        viewModelScope.launch {
            val fetchedNews = repository.getNews(apiKey)
            _newsList.postValue(fetchedNews as List<NewsModel>?)
        }
    }

    fun isFavorite(url: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val favorite = repository.getFavoriteNews(url)
            result.postValue(favorite != null)
        }
        return result
    }
}