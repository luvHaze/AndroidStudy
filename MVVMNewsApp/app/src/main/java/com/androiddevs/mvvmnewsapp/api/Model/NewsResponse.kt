package com.androiddevs.mvvmnewsapp.api.Model

import com.androiddevs.mvvmnewsapp.api.Model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)