package uz.gita.newsapp_john.presentation.screens.login.viewmodel

import androidx.lifecycle.LiveData


interface LoginViewModel {
    val showProgress: LiveData<Boolean>
    val openHomeScreenLD: LiveData<Unit>
    val buttonEnabledState: LiveData<Boolean>
    val errorHandler: LiveData<String>
    fun signIn(name: String, email: String, password: String, remember: Boolean)
    fun textChanged(name: String, email: String, password: String)
}