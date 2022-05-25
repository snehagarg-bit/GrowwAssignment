package com.example.growwassignment.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.growwassignment.models.NewsModel

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: NewsModel)

    @Query("SELECT * FROM News_Table")
    fun getNewsFromDatabase() : LiveData<List<NewsModel>>


    @Delete
    fun deleteNews(news: NewsModel)


}