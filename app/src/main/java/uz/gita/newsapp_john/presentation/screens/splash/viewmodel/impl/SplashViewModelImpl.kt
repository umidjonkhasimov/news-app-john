package uz.gita.newsapp_john.presentation.screens.splash.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.newsapp_john.data.MySharedPreferences
import uz.gita.newsapp_john.presentation.screens.splash.viewmodel.SplashViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val myPrefs: MySharedPreferences
) : ViewModel(), SplashViewModel {

    override var openLoginPageLD = MutableLiveData<Unit>()
    override var openHomePageLD = MutableLiveData<Unit>()

    init {
        if (!myPrefs.isSignedIn) {
            openLoginPageLD.value = Unit
        } else {
            openHomePageLD.value = Unit
        }
    }
}