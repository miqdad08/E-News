package com.miqdad.e_news.ui

import com.miqdad.e_news.data.network.ArticlesItem
import com.miqdad.e_news.data.network.TopHeadlineResponse

interface OnItemClickCallback {
    fun onItemClicked(data: ArticlesItem)
}