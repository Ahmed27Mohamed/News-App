package com.a2004256_ahmedmohamed.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.a2004256_ahmedmohamed.newsapp.interfaces.RoomDao
import com.a2004256_ahmedmohamed.newsapp.model.RoomModel

@Database(entities = [RoomModel::class], version = 3)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): RoomDao

    companion object {
        @Volatile private var instance: NewsDatabase? = null

        fun getNewsRoomDatabase(context: Context): NewsDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "favorite_news_database"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
    }
}