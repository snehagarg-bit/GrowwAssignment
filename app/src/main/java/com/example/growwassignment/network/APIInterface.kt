package com.example.growwassignment.network

import com.example.growwassignment.models.News
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {


    //https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=your_api_key


    @GET("/v2/top-headlines")
    fun getNews(@Query("country") country : String, @Query("category") category : String?, @Query("apiKey") key : String) : Call<News>


}