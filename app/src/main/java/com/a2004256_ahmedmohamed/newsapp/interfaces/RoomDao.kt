package com.a2004256_ahmedmohamed.newsapp.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.a2004256_ahmedmohamed.newsapp.model.RoomModel
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

@Dao
interface RoomDao {

    @androidx.room.Query("SELECT * FROM favorite_news WHERE url = :url LIMIT 1")
    suspend fun getFavoriteNews(url: String): RoomModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteNews(roomModel: RoomModel)

    @Delete
    suspend fun deleteFavoriteNews(roomModel: RoomModel)

    @androidx.room.Query("SELECT * FROM favorite_news")
    suspend fun getAllFavoriteNews(): List<RoomModel>
}
