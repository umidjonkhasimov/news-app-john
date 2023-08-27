package uz.gita.newsapp_john.presentation.viewmodels.impl

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import uz.gita.newsapp_john.app.NewsApp
import uz.gita.newsapp_john.data.MySharedPreferences
import uz.gita.newsapp_john.data.local.entity.ArticleEntity
import uz.gita.newsapp_john.data.model.NewsResponse
import uz.gita.newsapp_john.domain.repository.NewsRepository
import uz.gita.newsapp_john.presentation.viewmodels.NewsViewModel
import uz.gita.newsapp_john.util.Resource
import uz.gita.newsapp_john.util.SPORTS
import uz.gita.newsapp_john.util.TECHNOLOGY
import javax.inject.Inject

@HiltViewModel
class NewsViewModelImpl @Inject constructor(
    app: Application,
    private val repository: NewsRepository,
    private val myPrefs: MySharedPreferences
) : AndroidViewModel(app), NewsViewModel {
    override val allNews = MutableLiveData<Resource<NewsResponse>>()
    override var allNewsResponse: NewsResponse? = null
    override var allNewsPage: Int = 1
    override val userName = MutableLiveData<String>()

    init {
        userName.value = myPrefs.currentUserFirstName
        getAllNews("us", "")
    }

    override fun getAllNews(countryCode: String, category: String) {
        if (hasInternetConnection()) {
            allNews.value = Resource.Loading()
            viewModelScope.launch {
                val response = repository.getBreakingNews(countryCode, allNewsPage, category)

                allNews.postValue(handleNewsResponse(response))
            }
        } else {
            allNews.postValue(Resource.Error("No Internet Connection"))
        }
    }

    override fun searchNews(searchQuery: String) {
        allNews.value = Resource.Loading()
        viewModelScope.launch {
            val response = repository.searchNews(searchQuery, allNewsPage)

            allNews.postValue(handleNewsResponse(response))
        }
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                allNewsPage++
                if (allNewsResponse == null)
                    allNewsResponse = result
                else {
                    val oldArticles = allNewsResponse?.articles
                    val newArticles = result.articles
                    oldArticles?.addAll(newArticles)
                }

                return Resource.Success(allNewsResponse ?: result)
            }
        }
        if (response.code() == 429) {
            return Resource.Error("ou have made too many requests recently.")
        }
        return Resource.Error(response.message())
    }

    override fun saveArticle(article: ArticleEntity) {
        viewModelScope.launch {
            repository.saveArticle(article)
        }
    }

    override fun getSavedArticles() = repository.getSavedArticles()

    override fun deleteArticle(article: ArticleEntity) {
        viewModelScope.launch {
            repository.deleteSavedArticle(article)
        }
    }

    override fun hasInternetConnection(): Boolean {
        val connManager = getApplication<NewsApp>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connManager.activeNetwork ?: return false
        val capabilities = connManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}