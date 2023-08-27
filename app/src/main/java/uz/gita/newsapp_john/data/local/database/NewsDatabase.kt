package uz.gita.newsapp_john.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.newsapp_john.data.converter.Converter
import uz.gita.newsapp_john.data.local.dao.NewsDao
import uz.gita.newsapp_john.data.local.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getDao(): NewsDao
}