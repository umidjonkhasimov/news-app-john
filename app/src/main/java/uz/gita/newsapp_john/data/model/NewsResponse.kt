package uz.gita.newsapp_john.data.model

import uz.gita.newsapp_john.data.local.entity.ArticleEntity

data class NewsResponse(
    val articles: MutableList<ArticleEntity>,
    val status: String,
    val totalResults: Int
)