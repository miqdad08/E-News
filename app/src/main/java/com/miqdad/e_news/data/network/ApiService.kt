package com.miqdad.e_news.data.network

import com.miqdad.e_news.BuildConfig.API_KEY
import com.miqdad.e_news.data.TopHeadlineResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getTopHeadlineNews(
        @Query("country") country : String = "US",
        @Query("apiKey") api_key : String = API_KEY
    ): Flowable<TopHeadlineResponse>

    @GET("everything")
    fun getNewsBySearch(
        @Query("q") search : String,
        @Query("apiKey") api_key : String = API_KEY
    ): Flowable<TopHeadlineResponse>

    @GET("top-headlines")
    fun getCategory(
        @Query("category") category : String,
        @Query("country") country : String = "US",
        @Query("apiKey") api_key : String = API_KEY
    ): Flowable<TopHeadlineResponse>
}