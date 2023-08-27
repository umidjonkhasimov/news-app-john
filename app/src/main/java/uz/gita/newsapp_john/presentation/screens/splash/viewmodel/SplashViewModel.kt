package uz.gita.newsapp_john.presentation.screens.splash.viewmodel

import androidx.lifecycle.MutableLiveData


interface SplashViewModel {
    var openLoginPageLD: MutableLiveData<Unit>
    var openHomePageLD: MutableLiveData<Unit>
}