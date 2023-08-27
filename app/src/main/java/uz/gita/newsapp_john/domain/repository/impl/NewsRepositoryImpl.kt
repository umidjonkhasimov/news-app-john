package uz.gita.newsapp_john.domain.repository.impl

import androidx.lifecycle.LiveData
import retrofit2.Response
import uz.gita.newsapp_john.data.local.dao.NewsDao
import uz.gita.newsapp_john.data.local.database.NewsDatabase
import uz.gita.newsapp_john.data.local.entity.ArticleEntity
import uz.gita.newsapp_john.data.model.NewsResponse
import uz.gita.newsapp_john.data.remote.api.NewsApi
import uz.gita.newsapp_john.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val db: NewsDao,
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getBreakingNews(countryCode: String, pageNumber: Int, category: String) =
        api.getBreakingNews(countryCode = countryCode, pageNumber = pageNumber, category = category)

    override suspend fun searchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse> =
        api.searchForNews(searchQuery, pageNumber)

    override suspend fun saveArticle(article: ArticleEntity) {
        db.insert(article)
    }

    override fun getSavedArticles(): LiveData<List<ArticleEntity>> =
        db.getAllArticles()

    override suspend fun deleteSavedArticle(article: ArticleEntity) {
        db.deleteArticle(article)
    }
}