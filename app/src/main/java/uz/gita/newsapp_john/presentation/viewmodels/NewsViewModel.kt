package uz.gita.newsapp_john.presentation.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.newsapp_john.data.local.entity.ArticleEntity
import uz.gita.newsapp_john.data.model.NewsResponse
import uz.gita.newsapp_john.util.Resource

interface NewsViewModel {
    val allNews: LiveData<Resource<NewsResponse>>
    var allNewsResponse: NewsResponse?
    var allNewsPage: Int
    val userName: LiveData<String>

    fun hasInternetConnection(): Boolean

    fun getAllNews(countryCode: String, category: String)
    fun searchNews(searchQuery: String)

    fun saveArticle(article: ArticleEntity)
    fun getSavedArticles(): LiveData<List<ArticleEntity>>
    fun deleteArticle(article: ArticleEntity)
}