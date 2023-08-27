package uz.gita.newsapp_john.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.newsapp_john.domain.repository.AuthRepository
import uz.gita.newsapp_john.domain.repository.NewsRepository
import uz.gita.newsapp_john.domain.repository.impl.AuthRepositoryImpl
import uz.gita.newsapp_john.domain.repository.impl.NewsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun bindNewsRepo(impl: NewsRepositoryImpl): NewsRepository

    @[Binds Singleton]
    fun bindAuthRepo(impl: AuthRepositoryImpl): AuthRepository

}