package uz.gita.newsapp_john.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.newsapp_john.data.model.Source
import java.io.Serializable

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable