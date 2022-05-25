package com.example.growwassignment.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.growwassignment.database.NewsDatabase
import com.example.growwassignment.models.Article
import com.example.growwassignment.models.News
import com.example.growwassignment.models.NewsModel
import com.example.growwassignment.network.APIClient
import com.example.growwassignment.network.APIInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository()
{
    val newsList = MutableLiveData<List<NewsModel>>()

    companion object {

        var newsDatabase: NewsDatabase? = null
        var newsList: LiveData<List<NewsModel>>? = null

        fun initializeDB(context: Context) : NewsDatabase {
            return NewsDatabase.getDataseClient(context)
        }

        fun insertNews(context: Context, news: NewsModel) {

            newsDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                newsDatabase!!.newsDao().insertNews(news)
            }
        }

        fun deleteNews(context: Context, news: NewsModel) {

            newsDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                newsDatabase!!.newsDao().deleteNews(news)
            }
        }

        fun getAllNews(context: Context) : LiveData<List<NewsModel>>? {

            newsDatabase = initializeDB(context)
            newsList = newsDatabase!!.newsDao().getNewsFromDatabase()
            return newsList
        }

    }

    // get news from API
    fun getNewsApiCall(category: String?): MutableLiveData<List<NewsModel>> {


        val call = APIClient.getInstance().create(APIInterface::class.java)
            .getNews("in", category, "5a3e054de1834138a2fbc4a75ee69053") //put your api key here

        call.enqueue(object : Callback<News> {
            override fun onResponse(
                call: Call<News>,
                response: Response<News>
            ) {

                val body = response.body()
                if (body != null) {
                    val tempNewsList = mutableListOf<NewsModel>()
                    body.articles.forEach {

                        tempNewsList.add(NewsModel(it.title, it.urlToImage, it.description,it.url))

                    }

                    newsList.value = tempNewsList

                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
            }
        })

        return newsList
    }


}