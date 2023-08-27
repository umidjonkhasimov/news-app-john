package uz.gita.newsapp_john.presentation.screens.profile.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.newsapp_john.data.MySharedPreferences
import uz.gita.newsapp_john.domain.repository.AuthRepository
import uz.gita.newsapp_john.presentation.screens.profile.viewmodel.ProfileViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(
    private val myPrefs: MySharedPreferences,
    private val authRepository: AuthRepository
) : ProfileViewModel, ViewModel() {
    override val userName = MutableLiveData<String>()
    override val openLoginScreenLiveData = MutableLiveData<Unit>()

    init {
        userName.value = myPrefs.currentUserFirstName
    }

    override fun logout() {
        openLoginScreenLiveData.value = Unit
        authRepository.sigOut()
    }
}