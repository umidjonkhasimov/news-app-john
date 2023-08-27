package uz.gita.newsapp_john.presentation.screens.signup.viewmodel

import androidx.lifecycle.LiveData

interface SignUpViewModel {
    val showProgress: LiveData<Boolean>
    val buttonEnabledState: LiveData<Boolean>
    val openHomePage: LiveData<Unit>
    val errorHandler: LiveData<String>
    fun signUp(name: String, email: String, password: String, remember: Boolean)
    fun textChanged(name: String, email: String, password: String)
}