package com.a2004256_ahmedmohamed.newsapp.interfaces

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://newsapi.org/"

    val api: RetrofitDao by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitDao::class.java)
    }
}