package uz.gita.newsapp_john.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.newsapp_john.data.MySharedPreferences
import uz.gita.newsapp_john.data.MySharedPreferencesImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SharedPrefModule {

    @Binds
    @Singleton
    fun provideSharedPref(impl: MySharedPreferencesImpl): MySharedPreferences
}