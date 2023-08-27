package uz.gita.newsapp_john.presentation.screens.article

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newsapp_john.R
import uz.gita.newsapp_john.databinding.ScreenArticleBinding
import uz.gita.newsapp_john.presentation.viewmodels.NewsViewModel
import uz.gita.newsapp_john.presentation.viewmodels.impl.NewsViewModelImpl

@AndroidEntryPoint
class ArticleScreen : Fragment(R.layout.screen_article) {
    private val viewModel: NewsViewModel by viewModels<NewsViewModelImpl>()
    private val binding: ScreenArticleBinding by viewBinding(ScreenArticleBinding::bind)
    private val args: ArticleScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article

        binding.apply {
            tvSourceArticle.text = article.source?.name
            tvTitleArticle.text = article.title
            Glide.with(requireContext())
                .load(article.urlToImage)
                .into(imageArticle)
            tvAuthorArticle.text = "By ${article.author}"
            tvPublishedArticle.text = "Published at ${article.publishedAt}"
            tvDescriptionArticle.text = article.description

            btnBackArticle.setOnClickListener {
                findNavController().popBackStack()
            }

            fab.setOnClickListener {
                viewModel.saveArticle(article)
                Snackbar.make(view, "Article Saved Successfully", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}