package uz.gita.newsapp_john.presentation.screens.login.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newsapp_john.data.MySharedPreferences
import uz.gita.newsapp_john.domain.repository.AuthRepository
import uz.gita.newsapp_john.presentation.screens.login.viewmodel.LoginViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val myPrefs: MySharedPreferences
) : LoginViewModel, ViewModel() {

    override val showProgress = MutableLiveData(false)
    override val buttonEnabledState = MutableLiveData<Boolean>()
    override val errorHandler = MutableLiveData<String>()
    override val openHomeScreenLD = MutableLiveData<Unit>()

    override fun signIn(name: String, email: String, password: String, remember: Boolean) {
        showProgress.value = true
        authRepository.signIn(email, password).onEach {
            it.onSuccess {
                showProgress.value = false
                myPrefs.currentUserFirstName = name
                if (remember) {
                    myPrefs.isSignedIn = true
                    myPrefs.currentUserEmail = email
                }
                openHomeScreenLD.value = Unit
            }
            it.onFailure { error ->
                showProgress.value = false
                errorHandler.value = error.message
            }
        }.launchIn(viewModelScope)
    }

    override fun textChanged(name: String, email: String, password: String) {
        buttonEnabledState.value =
            name.trim().isNotEmpty() &&
                    email.trim().endsWith(".com") &&
                    email.trim().length >= 6 &&
                    password.length >= 6
    }
}