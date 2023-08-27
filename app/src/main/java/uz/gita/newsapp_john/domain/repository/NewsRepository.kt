package uz.gita.newsapp_john.domain.repository

import androidx.lifecycle.LiveData
import retrofit2.Response
import uz.gita.newsapp_john.data.local.entity.ArticleEntity
import uz.gita.newsapp_john.data.model.NewsResponse

interface NewsRepository {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int, category: String): Response<NewsResponse>
    suspend fun searchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse>
    suspend fun saveArticle(article: ArticleEntity)
    fun getSavedArticles(): LiveData<List<ArticleEntity>>
    suspend fun deleteSavedArticle(article: ArticleEntity)
}