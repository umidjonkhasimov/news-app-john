package uz.gita.newsapp_john.presentation.screens.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newsapp_john.R
import uz.gita.newsapp_john.data.local.entity.ArticleEntity
import uz.gita.newsapp_john.databinding.ScreenSavedNewsBinding
import uz.gita.newsapp_john.presentation.adapters.NewsAdapter
import uz.gita.newsapp_john.presentation.viewmodels.impl.NewsViewModelImpl

@AndroidEntryPoint
class SavedNewsScreen : Fragment(R.layout.screen_saved_news) {
    private val viewModel by viewModels<NewsViewModelImpl>()
    private val binding: ScreenSavedNewsBinding by viewBinding(ScreenSavedNewsBinding::bind)
    private lateinit var adapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        adapter.setOnItemClickListener { article ->
            val action = SavedNewsScreenDirections.actionSavedNewsScreenToArticleScreen(article)
            findNavController().navigate(action)
        }

        val itemTouchHelper = object :
            ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or
                        ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or
                        ItemTouchHelper.RIGHT
            ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val article = adapter.differ.currentList[pos]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Deleted successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                }.show()
            }
        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

        viewModel.getSavedArticles().observe(viewLifecycleOwner, savedArticlesObserver)
    }

    private val savedArticlesObserver = Observer<List<ArticleEntity>> {
        adapter.differ.submitList(it)
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter()
        binding.apply {
            rvSavedNews.adapter = adapter
            rvSavedNews.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}