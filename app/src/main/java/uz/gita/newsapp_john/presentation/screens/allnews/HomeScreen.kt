package uz.gita.newsapp_john.presentation.screens.allnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.newsapp_john.R
import uz.gita.newsapp_john.data.model.NewsResponse
import uz.gita.newsapp_john.databinding.ItemTabBinding
import uz.gita.newsapp_john.databinding.ScreenHomeBinding
import uz.gita.newsapp_john.presentation.adapters.NewsAdapter
import uz.gita.newsapp_john.presentation.viewmodels.NewsViewModel
import uz.gita.newsapp_john.presentation.viewmodels.impl.NewsViewModelImpl
import uz.gita.newsapp_john.util.ALL
import uz.gita.newsapp_john.util.BUSINESS
import uz.gita.newsapp_john.util.ENTERTAINMENT
import uz.gita.newsapp_john.util.GENERAL
import uz.gita.newsapp_john.util.HEALTH
import uz.gita.newsapp_john.util.QUERY_PAGE_SIZE
import uz.gita.newsapp_john.util.Resource
import uz.gita.newsapp_john.util.SCIENCE
import uz.gita.newsapp_john.util.SEARCH_NEWS_TIME_DELAY
import uz.gita.newsapp_john.util.SPORTS
import uz.gita.newsapp_john.util.TECHNOLOGY

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {
    private val viewModel: NewsViewModel by viewModels<NewsViewModelImpl>()
    private val binding: ScreenHomeBinding by viewBinding(ScreenHomeBinding::bind)
    private lateinit var adapter: NewsAdapter
    private var job: Job? = null
    private var currentCategory: String = ""

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPos + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPos >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.getAllNews("us", currentCategory)
                isScrolling = false
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupTabLayout()

        adapter.setOnItemClickListener { article ->
            val action = HomeScreenDirections.actionBreakingNewsScreenToArticleScreen(article)
            findNavController().navigate(action)
        }

        binding.etSearch.addTextChangedListener { text ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                if (!text.isNullOrEmpty()) {
                    viewModel.allNewsPage = 1
                    viewModel.allNewsResponse = null
                    viewModel.searchNews(text.toString())
                } else {
                    viewModel.allNewsPage = 1
                    viewModel.allNewsResponse = null
                    viewModel.getAllNews("us", currentCategory)
                }
            }
        }
        viewModel.allNews.observe(viewLifecycleOwner, allNewsObserver)
        viewModel.userName.observe(viewLifecycleOwner, userNameObserver)
    }

    private fun setupTabLayout() {
        binding.apply {
            for (i in 0..7) {
                val item = ItemTabBinding.inflate(LayoutInflater.from(requireContext()))
                val tab = tabLayout.newTab().setCustomView(item.root)
                when (i) {
                    0 -> {
                        item.tvCategoryTitle.text = "Breaking"
                        tab.tag = ALL
                    }

                    1 -> {
                        item.tvCategoryTitle.text = "Business"
                        tab.tag = BUSINESS
                    }

                    2 -> {
                        item.tvCategoryTitle.text = "Entertainment"
                        tab.tag = ENTERTAINMENT
                    }

                    3 -> {
                        item.tvCategoryTitle.text = "General"
                        tab.tag = GENERAL
                    }

                    4 -> {
                        item.tvCategoryTitle.text = "Health"
                        tab.tag = HEALTH
                    }

                    5 -> {
                        item.tvCategoryTitle.text = "Science"
                        tab.tag = SCIENCE
                    }

                    6 -> {
                        item.tvCategoryTitle.text = "Sports"
                        tab.tag = SPORTS
                    }

                    7 -> {
                        item.tvCategoryTitle.text = "Technology"
                        tab.tag = TECHNOLOGY
                    }
                }
                tabLayout.addTab(tab)
            }

            tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    currentCategory = tab?.tag as String
                    viewModel.allNewsPage = 1
                    viewModel.allNewsResponse = null
                    viewModel.getAllNews("us", currentCategory)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
//                    TODO("Not yet implemented")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
//                    TODO("Not yet implemented")
                }
            })
        }
    }

    private val userNameObserver = Observer<String> {
        binding.greetingsHome.text = "$it, How are you doing today?"
    }

    private val allNewsObserver = Observer<Resource<NewsResponse>> { response ->
        when (response) {
            is Resource.Success -> {
                hideLoadingProgressBar()
                response.data?.let {
                    adapter.differ.submitList(it.articles.toList())
                    val totalPages = it.totalResults / QUERY_PAGE_SIZE + 2
                    isLastPage = viewModel.allNewsPage == totalPages
                }
            }

            is Resource.Error -> {
                hideLoadingProgressBar()
                response.message?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }

            is Resource.Loading -> {
                showLoadingProgressBar()
            }
        }
    }

    private fun hideLoadingProgressBar() {
        binding.loadingProgressBar.visibility = View.INVISIBLE
    }

    private fun showLoadingProgressBar() {
        binding.loadingProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter()
        binding.apply {
            rvBreakingNews.adapter = adapter
            rvBreakingNews.layoutManager = LinearLayoutManager(requireContext())
            rvBreakingNews.addOnScrollListener(scrollListener)
        }
    }
}