package uz.gita.newsapp_john.presentation.adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.newsapp_john.R
import uz.gita.newsapp_john.data.local.entity.ArticleEntity
import uz.gita.newsapp_john.databinding.ItemArticlePreviewBinding
import uz.gita.newsapp_john.util.convertServerTimestampToRelativeTime
import java.time.LocalDateTime

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var onItemClickListener: ((ArticleEntity) -> Unit)? = null

    fun setOnItemClickListener(block: (ArticleEntity) -> Unit) {
        onItemClickListener = block
    }

    inner class NewsViewHolder(private val binding: ItemArticlePreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = differ.currentList[position]
            binding.apply {
                Glide.with(root)
                    .load(item.urlToImage)
                    .placeholder(R.drawable.img_placeholder)
                    .into(ivArticleImage)

                tvSource.text = item.source?.name
                tvTitle.text = item.title
//                tvDescription.text = item.description
                val time = convertServerTimestampToRelativeTime(item.publishedAt!!)
                tvPublishedAt.text = time
                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<ArticleEntity>() {
        override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}
