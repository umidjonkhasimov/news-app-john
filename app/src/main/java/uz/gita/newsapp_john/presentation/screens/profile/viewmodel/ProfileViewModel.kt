package uz.gita.newsapp_john.presentation.screens.profile.viewmodel

import androidx.lifecycle.LiveData

interface ProfileViewModel {
    fun logout()
    val userName: LiveData<String>
    val openLoginScreenLiveData: LiveData<Unit>
}