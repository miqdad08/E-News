package com.miqdad.e_news.ui

import com.miqdad.e_news.data.ArticlesItem

interface OnItemClickCallback {
    fun onItemClicked(data: ArticlesItem)
}